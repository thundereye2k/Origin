package win.crune.origin.scoreboard.sidebar.provider;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import win.crune.origin.profile.Profile;

import java.util.List;

public class OriginProvider implements SidebarProvider {

    @Override
    public String getTitle(Profile profile) {
        return "&6&lOrigin &6Network";
    }

    @Override
    public List<String> getLines(Profile profile) {
        List<String> toReturn = Lists.newArrayList();

        toReturn.add("&r ");
        toReturn.add("&6Online: &f" + Bukkit.getOnlinePlayers().size());
        toReturn.add("&r ");
        toReturn.add("&eThis line is actually long as fuck lmao 123");
        toReturn.add("&r ");

        return toReturn;
    }
}
