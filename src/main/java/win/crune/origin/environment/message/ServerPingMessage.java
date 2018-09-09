package win.crune.origin.environment.message;

import win.crune.origin.environment.Server;
import win.crune.origin.environment.State;
import win.crune.redismessenger.message.RedisMessage;

public class ServerPingMessage implements RedisMessage {

    private Server server;

    public ServerPingMessage(Server server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "server-ping";
    }

    @Override
    public String[] getMessage() {
        StringBuilder onlinePlayers = new StringBuilder();
        server.getOnlinePlayers().forEach(s -> onlinePlayers.append(s).append("$"));

        StringBuilder whitelistedPlayers = new StringBuilder();
        server.getWhitelistedPlayers().forEach(s -> whitelistedPlayers.append(s).append("$"));

        return new String[]{
                server.getGroup(), server.getName(), server.getState().name(),
                server.getStage().name(), onlinePlayers.toString(), whitelistedPlayers.toString(),
                server.getMaxPlayers() + ""
        };
    }
}
