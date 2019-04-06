package org.rootedinc.prometheus.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rootedinc.prometheus.interfaces.command.CommandEx;

import static org.rootedinc.prometheus.Prometheus.prefixifyMsg;

public class CmdClearToolbar extends CommandEx {
    /**
     * Constructor to satisfy data.
     */
    public CmdClearToolbar() {
        super("cleartoolbar");
    }

    /**
     * The actual code.
     * @param commandSender The initiator of the command.
     * @param command       The command object.
     * @param cmdLabel      The command as the player typed.
     * @param cmdArgs       The arguments passed to the command.
     */
    @Override
    public void mainCommand(CommandSender commandSender, Command command, String cmdLabel, String[] cmdArgs) {
        // Has to be player.
        if (! (commandSender instanceof Player)) {
            commandSender.sendMessage(prefixifyMsg("This command is only for players."));
        }

        Player p = (Player) commandSender;
        for(int i = 0; i < 9; i++) {
            if (p.getInventory().getItem(i) != null) {
                p.getInventory().getItem(i).setType(Material.AIR);
            }
        }

        p.sendMessage(prefixifyMsg("Toolbar cleared!"));
    }
}
