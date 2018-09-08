package win.crune.origin.store;

import win.crune.origin.store.defaults.NamedStore;
import win.crune.origin.store.defaults.UniqueStore;

import java.util.UUID;

public class Stores {

    /**
     * Create a new hashbased string store
     * See {@link NamedStore}
     * @param <E> the object stored
     * @return new namedStore
     */
    public static <E extends Storeable<String>> NamedStore<E> newNamedStore() {
        return new NamedStore<>();
    }

    /**
     * Create a new hashbased {@link UUID} store
     * See {@link UniqueStore}
     * @param <E> the object stored
     * @return new uniqueStore
     */
    public static <E extends Storeable<UUID>> UniqueStore<E> newUniqueStore() {
        return new UniqueStore<>();
    }
}
