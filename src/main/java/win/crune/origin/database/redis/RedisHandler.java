package win.crune.origin.database.redis;

import lombok.Getter;
import redis.clients.jedis.JedisPool;
import win.crune.origin.Origin;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.environment.State;
import win.crune.origin.environment.listener.ServerRedisListener;
import win.crune.origin.environment.message.ServerChangeStateMessage;
import win.crune.origin.profile.redis.listener.StaffRedisListener;
import win.crune.origin.handler.Handler;
import win.crune.redismessenger.RedisManager;
import win.crune.redismessenger.impl.SimpleRedisMessenger;
import win.crune.redismessenger.messenger.RedisMessenger;

@Getter
public class RedisHandler implements Handler {

    private RedisMessenger redisMessenger;
    private JedisPool jedisPool;

    @Override
    public void onEnable() {
        this.redisMessenger = RedisManager.getInstance().getRedisMessenger();
        this.jedisPool = RedisManager.getInstance().getJedisPool(); //TODO authenticate with settings config
    }

    @Override
    public void onDisable() {
        ServerHandler serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
        serverHandler.getServerPingThread().stop();

        ServerChangeStateMessage serverChangeStateMessage = new ServerChangeStateMessage(serverHandler.getCurrentServer(), State.OFFLINE);
        redisMessenger.sendMessage(serverChangeStateMessage);
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
