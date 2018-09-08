package win.crune.origin.store.defaults;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import win.crune.origin.store.Store;
import win.crune.origin.store.Storeable;

import java.util.Set;
import java.util.stream.Stream;

public class OriginStore<T extends Storeable> implements Store<T> {

    private Set<T> stored;

    public OriginStore() {
        this.stored = Sets.newHashSet();
    }

    @Override
    public Set<T> getAll() {
        return ImmutableSet.copyOf(stored);
    }

    @Override
    public T get(Object id) {
        if (stored.isEmpty()) {
            return null;
        }

        Stream<T> stream = getAll().stream();
        return stream.filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public void add(T t) {
        stored.add(t);
    }

    @Override
    public void remove(T t) {
        stored.remove(t);
    }
}
