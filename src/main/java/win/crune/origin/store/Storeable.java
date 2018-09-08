package win.crune.origin.store;

/**
 * Implemented by objects that should be stored in a store
 * see {@link win.crune.origin.profile.Profile}, {@link win.crune.origin.store.defaults.UniqueStore}
 * @param <T> the id object
 */
public interface Storeable<T> {

    /**
     * Get the id for this object
     * @return the id
     */
    T getId();

    /**
     * Set the id for this object
     * @param t the id
     */
    void setId(T t);

}
