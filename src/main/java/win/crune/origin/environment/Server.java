package win.crune.origin.environment;

import lombok.Getter;
import lombok.Setter;
import win.crune.origin.store.Storeable;

import java.util.Set;

/**
 * Server class
 */
public class Server implements Storeable<String> {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String group;

    @Getter
    @Setter
    private Set<String> onlinePlayers;

    @Getter
    @Setter
    private int maxPlayers;

    @Getter
    @Setter
    private Set<String> whitelistedPlayers;

    @Getter
    @Setter
    private State state = State.OFFLINE;

    @Getter
    @Setter
    private Stage stage;

    public Server(String name, String group) {
        this.name = name;
        this.group = group;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public void setId(String s) {
        this.name = s;
    }
}
