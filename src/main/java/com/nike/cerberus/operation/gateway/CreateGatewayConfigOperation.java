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

package com.nike.cerberus.operation.gateway;

import com.nike.cerberus.command.gateway.CreateGatewayConfigCommand;
import com.nike.cerberus.domain.cloudformation.BaseOutputs;
import com.nike.cerberus.domain.cloudformation.CmsParameters;
import com.nike.cerberus.domain.cloudformation.VaultParameters;
import com.nike.cerberus.domain.configuration.GatewayConfiguration;
import com.nike.cerberus.domain.environment.StackName;
import com.nike.cerberus.domain.template.GatewayConfigurationInput;
import com.nike.cerberus.generator.GatewayConfigGenerator;
import com.nike.cerberus.operation.Operation;
import com.nike.cerberus.store.ConfigStore;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Operation to create the gateway configuration.
 */
public class CreateGatewayConfigOperation implements Operation<CreateGatewayConfigCommand> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GatewayConfigGenerator gatewayConfigGenerator;

    private final ConfigStore configStore;

    @Inject
    public CreateGatewayConfigOperation(final GatewayConfigGenerator gatewayConfigGenerator,
                                      final ConfigStore configStore) {
        this.gatewayConfigGenerator = gatewayConfigGenerator;
        this.configStore = configStore;
    }

    @Override
    public void run(final CreateGatewayConfigCommand command) {
        final BaseOutputs baseOutputs = configStore.getBaseStackOutputs();
        final CmsParameters cmsParameters = configStore.getCmsStackParamters();
        final VaultParameters vaultParameters = configStore.getVaultStackParamters();
        final GatewayConfigurationInput input = new GatewayConfigurationInput()
                .setDashboardHost(baseOutputs.getDashboardBucketWebsiteUrl().replaceAll("http://|https://", ""))
                .setCmsHost(cnameToHost(cmsParameters.getCname()))
                .setVaultHost(cnameToHost(vaultParameters.getCname()))
                .setGatewayHost(command.getHostname());

        logger.info("Generating the Gateway configuration.");
        final GatewayConfiguration gatewayConfiguration = gatewayConfigGenerator.generate(input);

        logger.info("Uploading the Gateway configuration to the configuration bucket.");
        configStore.storeGatewayConfig(gatewayConfiguration);

        logger.info("Uploading complete.");
    }

    @Override
    public boolean isRunnable(final CreateGatewayConfigCommand command) {
        boolean isRunnable = true;
        final String cmsStackId = configStore.getStackId(StackName.CMS);

        if (StringUtils.isBlank(cmsStackId)) {
            logger.error("No CMS stack present for the specified environment, please create that first.");
            isRunnable = false;
        }

        return isRunnable;
    }

    /**
     * Removes the final '.' from the CNAME.
     *
     * @param cname The cname to convert
     * @return The host derived from the CNAME
     */
    private String cnameToHost(final String cname) {
        return cname.substring(0, cname.length() - 1);
    }
}
