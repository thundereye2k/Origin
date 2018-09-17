package win.crune.origin.scoreboard.sidebar.provider;

import com.google.common.collect.Lists;
import win.crune.origin.Origin;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.profile.Profile;

import java.util.List;

/**
 * This is just a testing template
 */
public class OriginProvider implements SidebarProvider {

    private ServerHandler serverHandler;

    public OriginProvider() {
        this.serverHandler = (ServerHandler) Origin.getInstance().getHandlerStore().get("server");
    }

    @Override
    public String getTitle(Profile profile) {
        return "&6&lOrigin &7❘ &f" + serverHandler.getCurrentServer().getName();
    }

    @Override
    public List<String> getLines(Profile profile) {
        List<String> toReturn = Lists.newArrayList();

        toReturn.add("&7&m------------------------");

        int[] online = {0};
        serverHandler.getServerMap().forEach(serverStore -> {
            serverStore.getAll().forEach(server -> online[0] += server.getOnlinePlayers().size());
        });

        toReturn.add("&6Online&7: &f" + online[0]);

        if (profile.getPlayer().isFlying()) {
            toReturn.add("&r ");
            toReturn.add("&dYou are flying!");
        }

        if (profile.getPlayer().isSneaking()) {
            toReturn.add("&r ");
            toReturn.add("&cYou are sneaking!");
        }

        toReturn.add("&7&m------------------------");

        return toReturn;
    }
}
