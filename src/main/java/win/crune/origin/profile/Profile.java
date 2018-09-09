package win.crune.origin.profile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import win.crune.origin.rank.Rank;
import win.crune.origin.store.Storeable;

import java.util.UUID;

@Getter
@Setter
public class Profile implements Storeable<UUID> {

    private UUID uuid;
    private Rank rank;
    private String name;
    private String displayName;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDisplayName() {
        return displayName == null ? ChatColor.WHITE + getPlayer().getDisplayName() : displayName;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
