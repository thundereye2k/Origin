package win.crune.origin.database.redis;

import win.crune.origin.handler.Handler;
import win.crune.redismessenger.impl.SimpleRedisMessenger;
import win.crune.redismessenger.messenger.RedisMessenger;

public class RedisHandler implements Handler {

    private RedisMessenger redisMessenger;

    @Override
    public void onEnable() {
        this.redisMessenger = new SimpleRedisMessenger();
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getId() {
        return "redis";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
