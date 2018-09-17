package win.crune.origin.profile.redis.listener;

import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.chat.Chat;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.environment.Server;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;
import win.crune.redismessenger.example.ExampleRedisListener;
import win.crune.redismessenger.example.ExampleRedisMessage;
import win.crune.redismessenger.listener.RedisListener;
import win.crune.redismessenger.listener.annotation.RedisHandler;
import win.crune.redismessenger.message.RedisMessage;

import java.util.Arrays;
import java.util.UUID;

public class StaffRedisListener implements RedisListener {

    @RedisHandler(name = "staff-join")
    public void onMessage(RedisMessage redisMessage) {
        ProfileHandler profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
        ServerHandler serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");

        String[] args = redisMessage.getMessage();

        Profile profile = profileHandler.getProfileStore().get(UUID.fromString(args[0]));
        Server server = serverHandler.getServer(args[1], args[2]);

        if (profile == null) {
            profile = profileHandler.getOfflineProfile(UUID.fromString(args[0]));
        }

        String message = "&9[Staff] &b" + profile.getName() + " has joined " + server.getGroup() + " &7(" + server.getName() + ")&b.";
        Bukkit.broadcast(Chat.color(message), "origin.staff");
    }
}
