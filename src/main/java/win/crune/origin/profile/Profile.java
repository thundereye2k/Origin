package win.crune.origin.profile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import win.crune.origin.profile.setting.Setting;
import win.crune.origin.rank.Rank;
import win.crune.origin.scoreboard.sidebar.Sidebar;
import win.crune.origin.scoreboard.sidebar.provider.SidebarProvider;
import win.crune.origin.store.Store;
import win.crune.origin.store.Storeable;
import win.crune.origin.store.Stores;

import java.util.UUID;

@Getter
@Setter
public class Profile implements Storeable<UUID> {

    private UUID uuid;
    private Rank rank;
    private String name;
    private String displayName;
    private SidebarProvider sidebarProvider;
    private Store<Setting> settingStore;
    private Sidebar sidebar;
    private boolean online;

    public Profile(UUID uuid) {
        this.uuid = uuid;

        this.settingStore = Stores.newNamedStore();
        this.settingStore.add(new Setting("sidebar", true, "Sidebar"));
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
