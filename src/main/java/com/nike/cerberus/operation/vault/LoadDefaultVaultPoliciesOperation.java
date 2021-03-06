/*
 * Copyright (c) 2016 Nike, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nike.cerberus.operation.vault;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nike.cerberus.command.vault.LoadDefaultVaultPoliciesCommand;
import com.nike.cerberus.operation.Operation;
import com.nike.cerberus.vault.VaultAdminClientFactory;
import com.nike.vault.client.VaultAdminClient;
import com.nike.vault.client.model.VaultPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * Operation for loading the default Vault policies expected by Cerberus.
 */
public class LoadDefaultVaultPoliciesOperation implements Operation<LoadDefaultVaultPoliciesCommand> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String defaultPoliciesPath = "/vault/default-policies.json";

    private final VaultAdminClientFactory vaultAdminClientFactory;

    @Inject
    public LoadDefaultVaultPoliciesOperation(final VaultAdminClientFactory vaultAdminClientFactory) {
        this.vaultAdminClientFactory = vaultAdminClientFactory;
    }

    @Override
    public void run(final LoadDefaultVaultPoliciesCommand command) {
        logger.info("Getting client for Vault leader.");
        final Optional<VaultAdminClient> leaderClient = vaultAdminClientFactory.getClientForLeader();

        if (leaderClient.isPresent()) {
            for (Map.Entry<String, VaultPolicy> entry : getDefaultPolicies().entrySet()) {
                logger.info("Loading policy, {}.", entry.getKey());
                leaderClient.get().putPolicy(entry.getKey(), entry.getValue());
            }

            logger.info("All default policies loaded.");
        } else {
            throw new IllegalStateException("Unable to determine Vault leader, aborting...");
        }
    }

    @Override
    public boolean isRunnable(final LoadDefaultVaultPoliciesCommand command) {
        return vaultAdminClientFactory.getClientForLeader().isPresent();
    }

    private Map<String, VaultPolicy> getDefaultPolicies() {
        final ObjectMapper objectMapper = new ObjectMapper();
        final TypeReference<Map<String, VaultPolicy>> typeReference = new TypeReference<Map<String, VaultPolicy>>() {};

        try (InputStream defaultPolicies = getClass().getResourceAsStream(defaultPoliciesPath)) {
            return objectMapper.readValue(defaultPolicies, typeReference);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read the default policies document from the classpath!", e);
        }
    }
}
