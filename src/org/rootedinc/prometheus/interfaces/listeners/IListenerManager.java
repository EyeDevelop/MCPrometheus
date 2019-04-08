package org.rootedinc.prometheus.interfaces.listeners;

public interface IListenerManager {

    /**
     * Function to register listener.
     *
     * @param listener The listener to be registered.
     */
    void registerListener(IEventListener listener);

    /**
     * Function that registers all known listeners with Bukkit. If you have advanced events, also call doBukkitRegister().
     */
    void doSimpleBukkitRegister();

    /**
     * Function that registers all known advanced listeners with Bukkit.
     */
    void doBukkitRegister();

    /**
     * Function to be ran when the plugin is reloaded.
     */
    void onReload();

}
