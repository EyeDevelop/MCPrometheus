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

    // Keep track of some required data.
    private String commandName;

    /**
     * Constructor to satisfy data.
     *
     * @param commandName The name of this command.
     */
    public CommandEx(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public void registerSubCommand(String name, ISubCommand subCommand) {
        // Add the subcommand to the HashMap.
        subCommands.put(name, subCommand);
    }

    @Override
    public abstract void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs);

    @Override
    public void displayHelp(CommandSender commandSender, String cmdLabel, int pageID) {
        // Let user know which command we're sending help for.
        int numPages = (int) Math.floor(subCommands.size() / 5) + 1;

        // There is a maximum.
        if (pageID > numPages)
            pageID = numPages;

        // There is also a minimum.
        if (pageID < 2)
            pageID = 1;

        // Notify user.
        commandSender.sendMessage(prefixifyMsg("Help for " + cmdLabel));
        commandSender.sendMessage(prefixifyMsg(String.format("Page %d of %d", pageID, numPages)));

        // Make a list of keys.
        Object[] subCommandValues = subCommands.values().toArray();

        // Show all the subcommands and their short description.
        for (int i = (pageID - 1) * 5; i < Math.min(pageID * 5, subCommands.size()); i++) {
            // Get the subcommand.
            ISubCommand subCommand = (ISubCommand) subCommandValues[i];

            // Print the help.
            String helpMessage = subCommand.getName() + ": " + subCommand.getUsage();
            helpMessage += "\n      " + subCommand.getShortDescription();

            commandSender.sendMessage(helpMessage);
        }
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

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

                // Create an empty variable.
                int pageID = 1;
                ISubCommand hlpSubCommand = null;

                // Help is called for a subcommand or page number.
                if (cmdArgs.length >= 2) {
                    try {
                        // Try to get a page number from user.
                        pageID = Integer.parseInt(cmdArgs[1]);
                    } catch (NumberFormatException e) {
                        // Help is called for a subcommand.
                        hlpSubCommand = subCommands.get(cmdArgs[1]);
                    }
                }

                // Check if subcommand help is requested.
                if (hlpSubCommand != null) {
                    hlpSubCommand.displayHelp(commandSender);

                    return true;
                }

                // Default to displaying main help.
                displayHelp(commandSender, cmdLabel, pageID);

                return true;
            } else {
                // Run the subcommand code.

                // Check if the subcommand is registered.
                if (subCommand != null) {

                    // Only pass the sub arguments.
                    String[] subArgs = Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length);
                    subCommand.run(commandSender, command, cmdLabel, subArgs);

                    return true;
                }
            }
        }

        // Display help because subcommand hasn't run properly.
        commandSender.sendMessage(prefixifyMsg("Subcommand not found, please run help."));

        // Don't display the usage of the command.
        return false;
    }
}