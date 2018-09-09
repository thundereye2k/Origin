package win.crune.origin.profile.redis.listener;

import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.chat.Chat;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.environment.Server;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;
import win.crune.redismessenger.listener.RedisListener;
import win.crune.redismessenger.listener.annotation.RedisHandler;
import win.crune.redismessenger.message.RedisMessage;

import java.util.UUID;

public class StaffRedisListener implements RedisListener {

    private ProfileHandler profileHandler;
    private ConfigHandler configHandler;
    private ServerHandler serverHandler;

    public StaffRedisListener() {
        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
        this.configHandler = (ConfigHandler) Origin.getInstance().getHandlerStore().get("config");
        this.serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
    }

    @RedisHandler(name = "staff-join")
    public void onMessage(RedisMessage redisMessage) {
        String[] args = redisMessage.getMessage();

        Profile profile = profileHandler.getProfileStore().get(UUID.fromString(args[0]));
        Server server = serverHandler.getServer(args[1], args[2]);

        String message = "&9[Staff] " + profile.getDisplayName() + " &bjoined " + server.getName() + ".";
        Bukkit.broadcast(Chat.color(message), "origin.alert.staff");
    }
}
