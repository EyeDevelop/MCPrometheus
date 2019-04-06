package org.rootedinc.prometheus.interfaces.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * A ListenerManager to simplify registering Listeners.
 */
public abstract class ListenerManager implements IListenerManager {

    private JavaPlugin pluginInstance;  // Need a plugin instance to register listeners.
    private List<IEventListener> listeners = new ArrayList<>();  // Keep track of actual listeners.

    /**
     * Constructor for the ListenManager.
     *
     * @param pluginParent The JavaPlugin parent for this ListenerManager.
     */
    public ListenerManager(JavaPlugin pluginParent) {
        this.pluginInstance = pluginParent;
    }

    /**
     * Function to register listener.
     *
     * @param listener The listener to be registered.
     */
    @Override
    public void registerListener(IEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Function that registers all known listeners with Bukkit.
     */
    @Override
    public void doSimpleBukkitRegister() {
        for (IEventListener l : listeners) {
            // Check if the listener is simple first.
            if (!l.isAdvanced()) {
                // Register the listener with Bukkit.
                Bukkit.getServer().getPluginManager().registerEvents(l, pluginInstance);
            }
        }
    }

    /**
     * Function that registers all known advanced listeners with Bukkit.
     */
    @Override
    public void doBukkitRegister() {
        for (IEventListener l : listeners) {
            // Check if the listener is advanced first.
            if (l.isAdvanced()) {
                // Register the event with all its properties.
                Bukkit.getServer().getPluginManager().registerEvent(l.getEventType(), l, l.getEventPriority(), l.getEventExecutor(), pluginInstance);
            }
        }
    }

    /**
     * Function to be ran when the plugin is reloaded.
     */
    @Override
    public void onReload() {
        for (IEventListener subListener : listeners) {
            // Run the listeners handler.
            subListener.onReload();
        }
    }
}
