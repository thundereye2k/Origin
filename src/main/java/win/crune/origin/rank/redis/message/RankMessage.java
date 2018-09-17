package win.crune.origin.rank.redis.message;

import win.crune.origin.rank.Rank;
import win.crune.redismessenger.message.RedisMessage;

public class RankMessage implements RedisMessage {

    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;

    private int action;
    private Rank rank;

    public RankMessage(int action, Rank rank) {
        this.action = action;
        this.rank = rank;
    }

    @Override
    public String getName() {
        return "rank";
    }

    @Override
    public String[] getMessage() {
        return new String[]{action + "", rank.getId().toString()};
    }
}
