package win.crune.origin.profile.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import win.crune.origin.Origin;
import win.crune.origin.event.ProfileJoinEvent;
import win.crune.origin.event.ProfileQuitEvent;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;
import win.crune.origin.scoreboard.sidebar.Sidebar;

public class PlayerListener implements Listener {

    private ProfileHandler profileHandler;

    public PlayerListener() {
        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
    }

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        Profile profile = profileHandler.getProfileStore().get(event.getUniqueId());

        if (profile == null) {
            profile = new Profile(event.getUniqueId());
            profileHandler.getProfileStore().add(profile);
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Profile profile = profileHandler.getProfileStore().get(player.getUniqueId());
        profile.setSidebar(new Sidebar(player));
        profile.setName(player.getName());

        ProfileJoinEvent profileJoinEvent = new ProfileJoinEvent(profile);
        Bukkit.getPluginManager().callEvent(profileJoinEvent);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = profileHandler.getProfileStore().get(player.getUniqueId());

        ProfileQuitEvent profileQuitEvent = new ProfileQuitEvent(profile);
        Bukkit.getPluginManager().callEvent(profileQuitEvent);
    }

}
