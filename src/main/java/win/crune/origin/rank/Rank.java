package win.crune.origin.rank;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import win.crune.origin.store.Storeable;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Rank implements Storeable<UUID> {

    private UUID uuid;
    private String name, prefix, suffix;
    private Set<String> permissions;
    private Rank inherited;
    private boolean global;

    public Rank(String name) {
        this(null, name, "", "", null, false);
    }

    public Rank(UUID uuid, String name, String prefix, String suffix, Rank inherited, boolean global) {
        this.uuid = uuid;
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.permissions = Sets.newHashSet();
        this.inherited = inherited;
        this.global = global;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }
}
