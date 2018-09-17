package win.crune.origin.scoreboard.sidebar;

import com.google.common.collect.Lists;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import win.crune.origin.chat.Chat;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Sidebar {

    @Getter
    private Scoreboard scoreboard;

    @Getter
    private Objective objective;

    private List<String> oldEntries;

    public Sidebar(Player player) {
        this.scoreboard = player.getScoreboard();

        if (scoreboard == Bukkit.getScoreboardManager().getMainScoreboard()) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }

        player.setScoreboard(scoreboard);

        this.oldEntries = Lists.newArrayList();

        /* Register a team for each sidebar line */
        IntStream.range(0, 16).forEach(i -> {
            Team team = scoreboard.getTeam("\u0000s" + i);

            if (team == null) {
                team = scoreboard.registerNewTeam("\u0000s" + i);
            }

            team.addEntry(ChatColor.values()[i].toString());
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
        while (entries.size() > 15) {
            entries.remove(entries.size() - 1);
        }

        /* Remove old entries */
        AtomicInteger slot = new AtomicInteger(entries.size());
        if (slot.get() < 15) {
            for (int i = oldEntries.size(); i > entries.size(); i--) {
                scoreboard.resetScores(ChatColor.values()[i].toString());
            }
        }

        /* Iterate through the entries and update teams prefix/suffix */
        entries.forEach(line -> {
            Team team = scoreboard.getTeam("\u0000s" + slot.get());

            String[] prefixSuffix = getPrefixSuffix(ChatColor.translateAlternateColorCodes('&', line));

            team.setPrefix(prefixSuffix[0]);
            team.setSuffix(prefixSuffix[1]);

            objective.getScore(ChatColor.values()[slot.get()].toString()).setScore(slot.get());
            slot.getAndDecrement();
        });

        this.oldEntries = entries;
    }

    private String[] getPrefixSuffix(String string) {
        String prefix = getPrefix(string);
        String suffix = getPrefix(ChatColor.getLastColors(prefix) + getSuffix(string));

        return new String[]{prefix, suffix};
    }

    private String getPrefix(String string) {
        return string.length() > 16 ? string.substring(0, 16) : string;
    }

    private String getSuffix(String string) {
        string = string.length() > 32 ? string.substring(0, 32) : string;

        return string.length() > 16 ? string.substring(16) : "";
    }
}
