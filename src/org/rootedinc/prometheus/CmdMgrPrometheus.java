package org.rootedinc.prometheus;

import org.bukkit.plugin.java.JavaPlugin;
import org.rootedinc.prometheus.commands.CmdClearToolbar;
import org.rootedinc.prometheus.commands.CmdPrometheus;
import org.rootedinc.prometheus.interfaces.command.CommandEx;
import org.rootedinc.prometheus.interfaces.command.CommandManager;

class CmdMgrPrometheus extends CommandManager {

    /**
     * Constructor that takes a parent plugin for registering commands.
     *
     * @param parentPlugin The parent plugin.
     */
    CmdMgrPrometheus(JavaPlugin parentPlugin) {
        super(parentPlugin);

        // Register commands.
        CommandEx cmdPrometheus = new CmdPrometheus();
        registerCommand(cmdPrometheus);

        CommandEx cmdToolbar = new CmdClearToolbar();
        registerCommand(cmdToolbar);
    }
}
