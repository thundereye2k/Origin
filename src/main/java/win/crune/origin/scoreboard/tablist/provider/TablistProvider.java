package win.crune.origin.scoreboard.tablist.provider;

import win.crune.origin.profile.Profile;

import java.util.Map;

public interface TablistProvider {

    /**
     * Get the tab header for a profile.
     *
     * @param profile the profile
     * @return string
     */
    String getHeader(Profile profile);

    /**
     * Get the tab footer for a profile.
     *
     * @param profile the profile
     * @return string
     */
    String getFooter(Profile profile);

    /**
     * Get the tab lines for a profile.
     *
     * @param profile the profile
     * @return map
     */
    Map<Integer, String> getTabLines(Profile profile);

}
