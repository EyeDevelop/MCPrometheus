package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public interface ICommandEx extends CommandExecutor {

    void registerSubCommand(String name, ISubCommand subCommand);
    void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs);
    void displayHelp(CommandSender commandSender, String cmdLabel);

    
    @Override
    boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings);
}