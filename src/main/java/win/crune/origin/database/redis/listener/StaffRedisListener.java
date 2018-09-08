package win.crune.origin.database.redis.listener;

import win.crune.origin.Origin;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;
import win.crune.redismessenger.listener.RedisListener;
import win.crune.redismessenger.listener.annotation.RedisHandler;
import win.crune.redismessenger.message.RedisMessage;

import java.util.UUID;

public class StaffRedisListener implements RedisListener {

    private ProfileHandler profileHandler;
    private ConfigHandler configHandler;

    public StaffRedisListener() {
        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
        this.configHandler = (ConfigHandler) Origin.getInstance().getHandlerStore().get("config");
    }

    @RedisHandler(name = "staff")
    public void onMessage(RedisMessage redisMessage) {
        String[] args = redisMessage.getMessage();

        String from = args[1];
        String to = args[2];
        Profile profile = profileHandler.getProfileStore().get(UUID.fromString(args[0]));

        if (to.equalsIgnoreCase(configHandler.getSettingsConfig().getString("server.id"))) {
            //TODO
        }
    }
}
