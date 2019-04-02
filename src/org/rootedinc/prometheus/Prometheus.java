package org.rootedinc.prometheus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.rootedinc.prometheus.commands.CmdPrometheus;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Main plugin class.
 */
public class Prometheus extends JavaPlugin {

    private static Prometheus instance;
    private static String pluginPrefix = (
            ChatColor.WHITE +
                    "[" +

                    ChatColor.RED +
                    "Prometheus" +

                    ChatColor.WHITE +
                    "]"
    );
    private static String consolePrefix = "[Prometheus]";
    private LstnPrometheus listenerManager = new LstnPrometheus(this);

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
    private static void Log(Level logLevel, String message) {
        // Log the message to the Bukkit console.
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
     * @return An instance of this plugin.
     */
    public static Prometheus getPluginInstance() {
        return instance;
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

        // Register the main command.
        Objects.requireNonNull(getCommand("prometheus")).setExecutor(new CmdPrometheus());
        Log(Level.FINE, "Registered commands.");

        // Register the listeners.
        listenerManager.doSimpleBukkitRegister();
        Log(Level.FINE, "Registered listeners");
    }
}
