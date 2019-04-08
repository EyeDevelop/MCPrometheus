package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface ICommandEx extends CommandExecutor {

    /**
     * Function to register a subcommand.
     *
     * @param name       The name of the subcommand.
     * @param subCommand The subcommand object itself.
     */
    void registerSubCommand(String name, ISubCommand subCommand);

    /**
     * The main method called when the command is executed without parameters.
     *
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     */
    void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs);

    /**
     * A simple function for printing help to a player.
     *
     * @param commandSender The initiator of the command.
     * @param cmdLabel      The command as the player typed.
     */
    void displayHelp(CommandSender commandSender, String cmdLabel, int pageID);

    /**
     * Getter for commandName.
     *
     * @return The name of this command.
     */
    String getCommandName();

    /**
     * The actual onCommand() that is called by Bukkit.
     *
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     * @return Whether to show the usage to the player. (false = yes, true = no).
     */
    @Override
    boolean onCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs);
}