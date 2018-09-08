package win.crune.origin.database.redis.message;

import win.crune.origin.profile.Profile;
import win.crune.redismessenger.message.RedisMessage;

public class StaffMessage implements RedisMessage {

    private Profile profile;

    public StaffMessage(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String getName() {
        return "staff";
    }

    @Override
    public String[] getMessage() {
        return new String[]{profile.getId().toString(), "server-from", "server-to"};
    }
}
