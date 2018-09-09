package win.crune.origin.environment;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.config.Config;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.environment.listener.ServerRedisListener;
import win.crune.origin.environment.message.ServerChangeStateMessage;
import win.crune.origin.environment.thread.ServerPingThread;
import win.crune.origin.handler.Handler;
import win.crune.origin.profile.redis.listener.StaffRedisListener;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

import java.util.Set;

public class ServerHandler implements Handler {

    @Getter
    private Server currentServer;
    private Set<Store<Server>> serverMap;
    @Getter
    private ServerPingThread serverPingThread;

    @Override
    public void onEnable() {
        ConfigHandler configHandler = (ConfigHandler) Origin.getInstance().getHandlerStore().get("config");
        Config settingsConfig = configHandler.getSettingsConfig();

        this.serverMap = Sets.newHashSet();
        this.currentServer = new Server(settingsConfig.getString("server.id"), settingsConfig.getString("server.group"));
        this.currentServer.setStage(Stage.valueOf(settingsConfig.getString("server.stage").toUpperCase()));

        registerServer(currentServer);

        RedisHandler redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");

        redisHandler.getRedisMessenger().registerMessages(new ServerRedisListener());
        redisHandler.getRedisMessenger().registerMessages(new StaffRedisListener());

        ServerChangeStateMessage serverChangeStateMessage = new ServerChangeStateMessage(currentServer, Bukkit.hasWhitelist() ? State.WHITELISTED : State.ONLINE);
        redisHandler.getRedisMessenger().sendMessage(serverChangeStateMessage);

        this.serverPingThread = new ServerPingThread();
        this.serverPingThread.start();
    }

    @Override
    public void onDisable() {
    }

    public void registerServer(Server server) {
        Store<Server> store = getServers(server.getGroup());

        if (store.getAll().isEmpty()) {
            serverMap.add(store);
        }

        store.add(server);
    }

    public Store<Server> getServers(String group) {
        Store<Server> toReturn = Stores.newNamedStore();

        ImmutableSet.copyOf(serverMap).forEach(serverStore -> {
            serverStore.getAll().forEach(server -> {
                if (server.getGroup().equalsIgnoreCase(group)) {
                    toReturn.add(server);
                }
            });
        });

        return toReturn;
    }

    public Server getServer(String group, String name) {
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
