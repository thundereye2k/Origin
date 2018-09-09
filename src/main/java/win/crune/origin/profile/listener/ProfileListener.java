package win.crune.origin.profile.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import win.crune.origin.Origin;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.event.ProfileJoinEvent;
import win.crune.origin.event.ProfileQuitEvent;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.redis.message.StaffJoinMessage;
import win.crune.origin.scoreboard.sidebar.provider.OriginProvider;

public class ProfileListener implements Listener {

    private ServerHandler serverHandler;
    private RedisHandler redisHandler;

    public ProfileListener() {
        this.serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
        this.redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");
    }

    @EventHandler
    public void onProfileJoinEvent(ProfileJoinEvent event) {
        Profile profile = event.getProfile();

        profile.setOnline(true);
        profile.setSidebarProvider(new OriginProvider());

        if (profile.getPlayer().hasPermission("origin.staff")) {
            StaffJoinMessage staffJoinMessage = new StaffJoinMessage(profile, serverHandler.getCurrentServer());
            redisHandler.getRedisMessenger().sendMessage(staffJoinMessage);
        }
    }

    @EventHandler
    public void onProfileQuitEvent(ProfileQuitEvent event) {
        Profile profile = event.getProfile();

        profile.setOnline(false);
    }

}
