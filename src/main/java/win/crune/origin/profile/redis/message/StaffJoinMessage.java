package win.crune.origin.profile.redis.message;

import win.crune.origin.environment.Server;
import win.crune.origin.profile.Profile;
import win.crune.redismessenger.message.RedisMessage;

import java.util.UUID;

public class StaffJoinMessage implements RedisMessage {

    private Profile profile;
    private Server server;

    public StaffJoinMessage(Profile profile, Server server) {
        this.profile = profile;
        this.server = server;
    }

    @Override
    public String getName() {
        return "staff-join";
    }

    @Override
    public String[] getMessage() {
        return new String[]{profile.getId().toString(), server.getGroup(), server.getName()};
    }
}
