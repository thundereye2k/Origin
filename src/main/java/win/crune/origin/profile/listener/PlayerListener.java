package win.crune.origin.profile.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import win.crune.origin.Origin;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.event.ProfileJoinEvent;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;

public class PlayerListener implements Listener {

    private ProfileHandler profileHandler;
    private ServerHandler serverHandler;
    private RedisHandler redisHandler;

    public PlayerListener() {
        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
        this.serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
        this.redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");
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

        ProfileJoinEvent profileJoinEvent = new ProfileJoinEvent(profile);
        Bukkit.getPluginManager().callEvent(profileJoinEvent);
    }

}
