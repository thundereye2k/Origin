package win.crune.origin;

import win.crune.origin.handler.Handler;
import win.crune.origin.store.Store;

/**
 * Will probably not use this
 */
public interface OriginAPI {

    Store<Handler> getHandlerStore();

    void registerHandler(Handler handler);

    void unregisterHandler(Handler handler);

}
