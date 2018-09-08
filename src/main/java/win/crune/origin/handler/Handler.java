package win.crune.origin.handler;

import win.crune.origin.store.Storeable;

/**
 * Handle things :)
 */
public interface Handler extends Storeable<String> {

    /**
     * When the handler is registered through {@link win.crune.origin.Origin}
     * this method will be called
     */
    void onEnable();

    /**
     * When the handler is unregistered through {@link win.crune.origin.Origin}
     * this method will be called
     */
    void onDisable();
}
