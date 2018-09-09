package win.crune.origin.environment;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.config.Config;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.environment.message.ServerChangeStateMessage;
import win.crune.origin.environment.thread.ServerPingThread;
import win.crune.origin.handler.Handler;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

import java.util.Map;

public class ServerHandler implements Handler {

    @Getter
    private Server currentServer;
    private Map<String, Store<Server>> serverMap;
    private ServerPingThread serverPingThread;

    @Override
    public void onEnable() {
        ConfigHandler configHandler = (ConfigHandler) Origin.getInstance().getHandlerStore().get("config");
        Config settingsConfig = configHandler.getSettingsConfig();

        this.serverMap = Maps.newHashMap();
        this.currentServer = new Server(settingsConfig.getString("server.id"), settingsConfig.getString("server.group"));
        this.currentServer.setStage(Stage.valueOf(settingsConfig.getString("server.stage").toUpperCase()));

        registerServer(currentServer);

        RedisHandler redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");
        ServerChangeStateMessage serverChangeStateMessage = new ServerChangeStateMessage(currentServer, Bukkit.hasWhitelist() ? State.WHITELISTED : State.ONLINE);
        redisHandler.getRedisMessenger().sendMessage(serverChangeStateMessage);

        this.serverPingThread = new ServerPingThread();
        this.serverPingThread.start();
    }

    @Override
    public void onDisable() {
        this.serverPingThread.stop();

        RedisHandler redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");
        ServerChangeStateMessage serverChangeStateMessage = new ServerChangeStateMessage(currentServer, State.OFFLINE);
        redisHandler.getRedisMessenger().sendMessage(serverChangeStateMessage);
    }

    public void registerServer(Server server) {
        Store<Server> store = getServers(server.getGroup());

        if (store == null) {
            store = Stores.newNamedStore();
        }

        store.add(server);
        serverMap.put(server.getGroup(), store);
    }

    public Store<Server> getServers(String group) {
        return ImmutableMap.copyOf(serverMap).get(group);
    }

    public Server getServer(String group, String name) {
        if (getServers(group) == null) {
            return null;
        }

        return getServers(group).get(name);
    }

    @Override
    public String getId() {
        return "server";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
