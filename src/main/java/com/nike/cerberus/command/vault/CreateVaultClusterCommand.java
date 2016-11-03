package com.nike.cerberus.command.vault;

import com.beust.jcommander.Parameters;
import com.beust.jcommander.ParametersDelegate;
import com.nike.cerberus.command.Command;
import com.nike.cerberus.command.StackDelegate;
import com.nike.cerberus.operation.Operation;
import com.nike.cerberus.operation.vault.CreateVaultClusterOperation;

import static com.nike.cerberus.command.vault.CreateVaultClusterCommand.COMMAND_NAME;

/**
 * Command to create the Vault cluster.
 */
@Parameters(commandNames = COMMAND_NAME, commandDescription = "Creates the Vault cluster.")
public class CreateVaultClusterCommand implements Command {

    public static final String COMMAND_NAME = "create-vault-cluster";

    @ParametersDelegate
    private StackDelegate stackDelegate = new StackDelegate();

    public StackDelegate getStackDelegate() {
        return stackDelegate;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public Class<? extends Operation<?>> getOperationClass() {
        return CreateVaultClusterOperation.class;
    }
}
