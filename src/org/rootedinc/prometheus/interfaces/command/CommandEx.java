package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashMap;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

/**
 * A custom class to simplify command and subcommand creation.
 */
public abstract class CommandEx implements ICommandEx {

    // Keep track of the subcommand names and how to execute them.
    private HashMap<String, ISubCommand> subCommands = new HashMap<>();

    /**
     * @param name       The name of the subcommand.
     * @param subCommand The subcommand object itself.
     */
    @Override
    public void registerSubCommand(String name, ISubCommand subCommand) {
        // Add the subcommand to the HashMap.
        subCommands.put(name, subCommand);
    }

    /**
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     */
    @Override
    public abstract void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs);

    /**
     * @param commandSender The initiator of the command.
     * @param cmdLabel      The command as the player typed.
     */
    @Override
    public void displayHelp(CommandSender commandSender, String cmdLabel) {
        // Let user know which command we're sending help for.
        commandSender.sendMessage(prefixifyMsg("Help for " + cmdLabel));

        // Show all the subcommands and their short description.
        for (ISubCommand subCommand : subCommands.values()) {
            String helpMessage = subCommand.getName() + ": " + subCommand.getUsage();
            helpMessage += "\n  " + subCommand.getShortDescription();

            commandSender.sendMessage(helpMessage);
        }
    }

    /**
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     * @return Whether to show the usage to the player. (false = yes, true = no).
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs) {
        // If the main command is executed, run the main command.
        if (cmdArgs.length == 0) {
            mainCommand(commandSender, command, cmdLabel, cmdArgs);
            return true;
        }

        // Otherwise, look for the subcommand and execute that.
        else {
            String subCommandName = cmdArgs[0];
            ISubCommand subCommand = subCommands.get(subCommandName);

            // Search for the subcommand in the registered subcommands.
            if (subCommandName.equalsIgnoreCase("help")) {
                // Help called.
                // Display help.

                if (cmdArgs.length >= 2) {
                    // Help is called for a subcommand.
                    // Find that subcommand.
                    String hlpSubCommandName = cmdArgs[1];
                    ISubCommand hlpSubCommand = subCommands.get(hlpSubCommandName);

                    // Only run that help if the subcommand is found, otherwise run the main help.
                    if (hlpSubCommand != null) {
                        hlpSubCommand.displayHelp(commandSender);

                        return true;
                    }
                }
            } else {
                // Subcommand was found.
                // Run the subcommand code.

                // Check if the subcommand is registered.
                if (subCommand == null) {
                    return false;
                }

                // Only pass the sub arguments.
                String[] subArgs = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length);
                subCommand.run(commandSender, command, cmdLabel, subArgs);

                return true;
            }
        }

        // Display help because subcommand hasn't run properly.
        displayHelp(commandSender, cmdLabel);

        // Don't display the usage of the command.
        return true;
    }
}