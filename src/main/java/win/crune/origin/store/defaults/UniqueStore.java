package win.crune.origin.store.defaults;

import win.crune.origin.store.Storeable;

import java.util.UUID;
import java.util.stream.Stream;

public class UniqueStore<T extends Storeable<UUID>> extends OriginStore<T> {

    @Override
    public T get(Object id) {
        return this.get((UUID) (id));
    }

    public T get(UUID id) {
        if (getAll().isEmpty()) {
            return null;
        }

        Stream<T> stream = getAll().stream();
        return stream.filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }
}
