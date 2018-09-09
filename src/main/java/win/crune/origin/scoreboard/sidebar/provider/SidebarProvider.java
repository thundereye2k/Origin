package win.crune.origin.scoreboard.sidebar.provider;

import win.crune.origin.profile.Profile;

import java.util.List;

public interface SidebarProvider {

    String getTitle(Profile profile);

    List<String> getLines(Profile profile);

}
