package com.nike.cerberus.generator;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.inject.Inject;
import com.nike.cerberus.domain.configuration.VaultConfiguration;
import com.nike.cerberus.domain.template.VaultConfigurationInput;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Generates the Vault configuration.
 */
public class VaultConfigGenerator {

    private final String vaultTemplatePath = "templates/vault.json.mustache";

    private final MustacheFactory mustacheFactory;

    @Inject
    public VaultConfigGenerator(final MustacheFactory mustacheFactory) {
        this.mustacheFactory = mustacheFactory;
    }

    public VaultConfiguration generate(final String consulDatacenter, final String vaultAclToken) {
        final VaultConfigurationInput input = new VaultConfigurationInput()
                .setDatacenter(consulDatacenter)
                .setAclToken(vaultAclToken);
        final Mustache templateCompiler = mustacheFactory.compile(vaultTemplatePath);
        final StringWriter configWriter = new StringWriter();

        try {
            templateCompiler.execute(configWriter, input).flush();
        } catch (IOException ioe) {
            throw new ConfigGenerationException("Failed to generate Vault configuration!", ioe);
        }

        return new VaultConfiguration().setConfig(configWriter.toString());
    }
}
