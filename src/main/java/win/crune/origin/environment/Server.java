package win.crune.origin.environment;

import lombok.Getter;
import lombok.Setter;
import win.crune.origin.store.Storeable;

import java.util.Set;

@Getter
@Setter
public class Server implements Storeable<String> {

    private String name;
    private String group;
    private Set<String> onlinePlayers;
    private int maxPlayers;
    private Set<String> whitelistedPlayers;
    private State state = State.OFFLINE;
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
