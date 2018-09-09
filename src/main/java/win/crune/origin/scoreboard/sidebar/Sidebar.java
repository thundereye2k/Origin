package win.crune.origin.scoreboard.sidebar;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import win.crune.origin.chat.Chat;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Sidebar {

    private Player player;
    private Scoreboard scoreboard;
    private Objective objective;

    private List<String> oldEntries;

    public Sidebar(Player player) {
        this.player = player;
        this.scoreboard = player.getScoreboard();

        if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        player.setScoreboard(scoreboard);

        this.oldEntries = Lists.newArrayList();

        IntStream.range(0, 16).forEach(i -> {
            SidebarEntry entry = new SidebarEntry("&r", i);
            Team team = scoreboard.getTeam("\u0000s" + i);

            if (team == null) {
                team = scoreboard.registerNewTeam("\u0000s" + i);
            }

            team.addEntry(entry.getName());
        });

        this.objective = scoreboard.getObjective("origin");
        if (this.objective == null) {
            this.objective = scoreboard.registerNewObjective("origin", "dummy");
        }

        this.objective.setDisplayName("test");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void update(String string) {
        if (string.length() > 32) {
            string = string.substring(0, 32);
        }

        objective.setDisplayName(Chat.color(string));
    }

    public void update(List<String> entries) {
        //Collections.reverse(entries);

        int[] i = {0};
        int[] j = {entries.size()};

        if (j[0] < 15) {
            for (int l = oldEntries.size(); l > entries.size(); l--) {
                SidebarEntry sidebarEntry = new SidebarEntry(oldEntries.get(l - 1), l);
                scoreboard.resetScores(sidebarEntry.getName());
            }
        }

        entries.forEach(s -> {
            SidebarEntry sidebarEntry = new SidebarEntry(Chat.color(s), i[0]);
            Team team = scoreboard.getTeam("\u0000s" + i[0]);

            try {
                SidebarEntry oldEntry = new SidebarEntry(oldEntries.get(i[0]), i[0]);

                if (!oldEntry.getName().equals(sidebarEntry.getName())) {
                    team.addEntry(sidebarEntry.getName());
                }
            } catch (IndexOutOfBoundsException e) {
                team.addEntry(sidebarEntry.getName());
            }

            team.setPrefix(sidebarEntry.getPrefix());
            team.setSuffix(sidebarEntry.getSuffix());
            objective.getScore(sidebarEntry.getName()).setScore(j[0]);

            i[0]++;
            j[0]--;
        });

        this.oldEntries = entries;
    }
}
