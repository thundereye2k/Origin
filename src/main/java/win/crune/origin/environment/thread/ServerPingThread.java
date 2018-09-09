package win.crune.origin.environment.thread;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import win.crune.origin.Origin;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.environment.Stage;
import win.crune.origin.environment.message.ServerChangeStateMessage;
import win.crune.origin.environment.message.ServerPingMessage;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ServerPingThread extends Thread {

    private ServerHandler serverHandler;
    private RedisHandler redisHandler;
    private ConfigHandler configHandler;

    public ServerPingThread() {
        setName("origin-server-ping");

        this.serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
        this.redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");
        this.configHandler = (ConfigHandler) Origin.getInstance().getHandlerStore().get("config");
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //long lines =(

            serverHandler.getCurrentServer().setMaxPlayers(Bukkit.getMaxPlayers());
            serverHandler.getCurrentServer().setOnlinePlayers(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toSet()));
            serverHandler.getCurrentServer().setWhitelistedPlayers(Bukkit.getWhitelistedPlayers().stream().map(OfflinePlayer::getName).collect(Collectors.toSet()));
            serverHandler.getCurrentServer().setStage(Stage.valueOf(configHandler.getSettingsConfig().getString("server.stage").toUpperCase()));
            serverHandler.getCurrentServer().setName(configHandler.getSettingsConfig().getString("server.id"));
            serverHandler.getCurrentServer().setGroup(configHandler.getSettingsConfig().getString("server.group"));

            this.redisHandler.getRedisMessenger().sendMessage(new ServerPingMessage(serverHandler.getCurrentServer()));

            if (serverHandler.getCurrentServer().getState() != win.crune.origin.environment.State.ONLINE  && !Bukkit.hasWhitelist() || (serverHandler.getCurrentServer().getState() != win.crune.origin.environment.State.WHITELISTED && Bukkit.hasWhitelist())) {
                this.redisHandler.getRedisMessenger().sendMessage(new ServerChangeStateMessage(serverHandler.getCurrentServer(), Bukkit.hasWhitelist() ? win.crune.origin.environment.State.WHITELISTED : win.crune.origin.environment.State.ONLINE));
            }
        }
    }
}
