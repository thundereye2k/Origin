package win.crune.origin.store.defaults;

import win.crune.origin.store.Storeable;

import java.util.stream.Stream;

public class NamedStore<T extends Storeable<String>> extends OriginStore<T> {

    @Override
    public T get(Object id) {
        return get(String.valueOf(id));
    }

    public T get(String id) {
        if (getAll().isEmpty()) {
            return null;
        }

        Stream<T> stream = getAll().stream();
        return stream.filter(t -> t.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
