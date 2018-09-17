package win.crune.origin.scoreboard.tablist;

import org.bukkit.entity.Player;
import win.crune.origin.scoreboard.sidebar.Sidebar;

import java.util.Map;

public class Tablist {

    private Sidebar sidebar;
    private Player player;
    private String header, footer;

    public Tablist(Sidebar sidebar, Player player) {
        this.sidebar = sidebar;
        this.player = player;

    }

    public void update(Map<Integer, String> lines) {

    }
}
