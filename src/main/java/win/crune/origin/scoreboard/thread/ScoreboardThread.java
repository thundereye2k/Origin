package win.crune.origin.scoreboard.thread;

import win.crune.origin.Origin;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;
import win.crune.origin.scoreboard.sidebar.provider.SidebarProvider;

import java.util.concurrent.TimeUnit;

public class ScoreboardThread extends Thread {

    private ProfileHandler profileHandler;

    public ScoreboardThread() {
        setName("origin-scoreboard");

        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            profileHandler.getProfileStore().getAll().stream().filter(Profile::isOnline).forEach(profile -> {
                if (profile.getSettingStore().get("sidebar").isEnabled()) {

                    SidebarProvider sidebarProvider = profile.getSidebarProvider();

                    if (sidebarProvider == null) {
                        return;
                    }

                    profile.getSidebar().update(sidebarProvider.getLines(profile));
                    profile.getSidebar().update(sidebarProvider.getTitle(profile));
                }
            });
        }
    }
}
