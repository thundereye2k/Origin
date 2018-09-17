package win.crune.origin.profile;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import win.crune.origin.Origin;
import win.crune.origin.handler.Handler;
import win.crune.origin.profile.listener.PlayerListener;
import win.crune.origin.profile.listener.ProfileListener;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

import java.util.UUID;

public class ProfileHandler implements Handler {

    @Getter
    private Store<Profile> profileStore;

    @Override
    public void onEnable() {
        this.profileStore = Stores.newUniqueStore();

        Origin.getInstance().getServer().getPluginManager().registerEvents(new PlayerListener(), Origin.getInstance());
        Origin.getInstance().getServer().getPluginManager().registerEvents(new ProfileListener(), Origin.getInstance());
    }

    @Override
    public void onDisable() {
    }

    public Profile getOfflineProfile(UUID uuid) {
        Profile profile = new Profile(uuid);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        profile.setName(offlinePlayer.getName());
        return profile;
    }

    @Override
    public String getId() {
        return "profile";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
