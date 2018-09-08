package win.crune.origin.profile;

import lombok.Getter;
import win.crune.origin.store.Storeable;

import java.util.UUID;

@Getter
public class Profile implements Storeable<UUID> {

    private UUID uuid;

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }
}
