package win.crune.origin.environment.message;

import win.crune.origin.environment.Server;
import win.crune.origin.environment.State;
import win.crune.redismessenger.message.RedisMessage;

public class ServerChangeStateMessage implements RedisMessage {

    private Server server;
    private State newState;

    public ServerChangeStateMessage(Server server, State newState) {
        this.server = server;
        this.newState = newState;
    }

    @Override
    public String getName() {
        return "server-change-state";
    }

    @Override
    public String[] getMessage() {
        return new String[]{server.getGroup(), server.getName(), server.getState().name(), newState.name()};
    }
}
