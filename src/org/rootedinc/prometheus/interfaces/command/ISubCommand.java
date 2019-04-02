package org.rootedinc.prometheus.interfaces.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ISubCommand {

    String getShortDescription();

    String getName();

    String getUsage();

    void run(CommandSender commandSender, Command command, String cmdLabel, String[] subArgs);

    void displayHelp(CommandSender commandSender);
}
