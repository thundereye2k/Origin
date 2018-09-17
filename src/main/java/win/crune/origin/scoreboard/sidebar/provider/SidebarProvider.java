package win.crune.origin.scoreboard.sidebar.provider;

import win.crune.origin.profile.Profile;

import java.util.List;

public interface SidebarProvider {

    /**
     * Get the sidebar title for a profile.
     * Splits at 32 characters.
     *
     * @param profile the profile
     * @return string
     */
    String getTitle(Profile profile);

    /**
     * Get the sidebar lines for a profile.
     * Splits at 32 characters.
     *
     * @param profile the profile
     * @return string list
     */
    List<String> getLines(Profile profile);

}
