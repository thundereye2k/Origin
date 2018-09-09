package win.crune.origin.scoreboard.tablist;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Tablist {

    private Player player;
    private Scoreboard scoreboard;
    private Objective objective;

    public Tablist(Player player, Scoreboard scoreboard, Objective objective) {
        this.player = player;
        this.scoreboard = scoreboard;
        this.objective = objective;
    }
}
