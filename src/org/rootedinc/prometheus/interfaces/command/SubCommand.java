package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

/**
 *
 */
public abstract class SubCommand implements ISubCommand {

    private String name;
    private String shortDescription;
    private String usage;

    /**
     * Constructor for the custom SubCommand.
     *
     * @param name             The subcommand name.
     * @param shortDescription A short description of what this subcommand does.
     * @param usage            The correct usage for this command.
     */
    public SubCommand(String name, String shortDescription, String usage) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.usage = usage;
    }

    /**
     * Function to retrieve the commands short description.
     *
     * @return The short description.
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Function to retrieve the name of the subcommand.
     *
     * @return The subcommand name.
     */
    public String getName() {
        return name;
    }

    /**
     * Function to retrieve the subcommands usage.
     *
     * @return The subcommands usage.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Actual code executed when the SubCommand is called.
     *
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      Exactly what the player typed.
     * @param subArgs       The arguments given to the subcommand.
     */
    @Override
    public abstract void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs);

    /**
     * Function for displaying information of this subcommand.
     *
     * @param commandSender The initiator of the command.
     */
    @Override
    public void displayHelp(CommandSender commandSender) {
        // Send help to sender.
        String helpMsg = "Help for " + name + ":\n";
        helpMsg += getUsage() + ": " + getShortDescription();

        commandSender.sendMessage(prefixifyMsg(helpMsg));
    }
}
