package win.crune.origin.environment.listener;

import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.chat.Chat;
import win.crune.origin.environment.Server;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.environment.Stage;
import win.crune.origin.environment.State;
import win.crune.redismessenger.listener.RedisListener;
import win.crune.redismessenger.listener.annotation.RedisHandler;
import win.crune.redismessenger.message.RedisMessage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ServerRedisListener implements RedisListener {

    private ServerHandler serverHandler;

    public ServerRedisListener() {
        this.serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
    }

    @RedisHandler(name = "server-change-state")
    public void onServerChangeStateMessage(RedisMessage redisMessage) {
        String[] args = redisMessage.getMessage();

        Server server = this.serverHandler.getServer(args[0], args[1]);

        if (server == null) {
            server = new Server(args[1], args[0]);
            serverHandler.registerServer(server);
        }

        State state = State.valueOf(args[3]);
        server.setState(state);

        String message =
                "&6[Server Manager] &fServer " + server.getName() + " is now &e" + server.getState() + "&f...";

        Bukkit.broadcast(Chat.color(message), "origin.alert.server");
    }

    @RedisHandler(name = "server-ping")
    public void onServerPingMessage(RedisMessage redisMessage) {
        String[] args = redisMessage.getMessage();

        Server server = this.serverHandler.getServer(args[0], args[1]);

        if (server == null) {
            server = new Server(args[1], args[0]);
            serverHandler.registerServer(server);
        }

        server.setState(State.valueOf(args[2]));
        server.setStage(Stage.valueOf(args[3]));
        server.setOnlinePlayers(Arrays.stream(args[4].split("$")).collect(Collectors.toSet()));
        server.setWhitelistedPlayers(Arrays.stream(args[5].split("$")).collect(Collectors.toSet()));
        server.setMaxPlayers(Integer.parseInt(args[6]));
    }

}
