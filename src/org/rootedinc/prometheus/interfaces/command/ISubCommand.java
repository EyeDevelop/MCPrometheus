package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ISubCommand {

    /**
     * Function to retrieve the commands short description.
     *
     * @return The short description.
     */
    String getShortDescription();

    /**
     * Function to retrieve the name of the subcommand.
     *
     * @return The subcommand name.
     */
    String getName();

    /**
     * Function to retrieve the subcommands usage.
     *
     * @return The subcommands usage.
     */
    String getUsage();

    /**
     * Actual code executed when the SubCommand is called.
     *
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      Exactly what the player typed.
     * @param subArgs       The arguments given to the subcommand.
     */
    void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs);

    /**
     * Function for displaying information of this subcommand.
     *
     * @param commandSender The initiator of the command.
     */
    void displayHelp(CommandSender commandSender);
}
