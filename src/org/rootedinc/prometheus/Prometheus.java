package org.rootedinc.prometheus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.rootedinc.prometheus.interfaces.command.CommandManager;
import org.rootedinc.prometheus.interfaces.listeners.ListenerManager;

import java.util.logging.Level;

/**
 * Main plugin class.
 */
public class Prometheus extends JavaPlugin {

    public static Prometheus instance;
    private static String pluginPrefix = (
            ChatColor.WHITE +
                    "[" +

                    ChatColor.RED +
                    "Prometheus" +

                    ChatColor.WHITE +
                    "]"
    );
    private ListenerManager listenerManager = new LstnMgrPrometheus(this);
    private CommandManager commandManager = new CmdMgrPrometheus(this);

    /**
     * The class constructor where the instance variable is set.
     */
    public Prometheus() {
        // Setup a reference to this class.
        instance = this;
    }

    /**
     * @param logLevel The Java Logger loglevel.
     * @param message  The message to log to console.
     */
    public static void Log(Level logLevel, String message) {
        // Log the message to the Bukkit console.
        String consolePrefix = "[Prometheus]";

        Bukkit.getLogger().log(logLevel, consolePrefix + " " + message);
    }

    /**
     * @param message The string to 'prefix-ify'.
     * @return The prefixed string.
     */
    public static String prefixifyMsg(String message) {
        return pluginPrefix + " " + message;
    }

    /**
     * Function that is called when the plugin is reloaded.
     */
    @Override
    public void onLoad() {
        super.onLoad();

        // Load the listeners reload handlers.
        listenerManager.onReload();
    }

    /**
     * Function that is called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        super.onDisable();
    }

    /**
     * Function that is called when the plugin is fully done loading.
     */
    @Override
    public void onEnable() {
        super.onEnable();

        // Notify the user the plugin is live.
        Log(Level.INFO, "Plugin is up and running!");

        // Register the commands.
        commandManager.bukkitRegister();
        Log(Level.FINE, "Registered commands.");

        // Register the listeners.
        listenerManager.doSimpleBukkitRegister();
        Log(Level.FINE, "Registered listeners");
    }
}
