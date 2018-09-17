package win.crune.origin.rank.thread;

import com.google.common.collect.Sets;
import win.crune.origin.Origin;
import win.crune.origin.profile.ProfileHandler;
import win.crune.origin.rank.Rank;
import win.crune.origin.rank.RankHandler;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PermissionThread extends Thread {

    private ProfileHandler profileHandler;

    public PermissionThread() {
        setName("origin-permission");

        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            RankHandler rankHandler = (RankHandler) Origin.getInstance().getHandlerStore().get("rank");

            profileHandler.getProfileStore().getAll().forEach(profile -> {
                if (!profile.isOnline()) {
                    return;
                }

                if (profile.getRank() == null || profile.getRank().getId() == null) {
                    profile.setRank(rankHandler.getDefaultRank());
                }

                Set<String> permissions = Sets.newHashSet();
                permissions.addAll(profile.getPermissions());
                permissions.addAll(appendPermissions(profile.getRank(), permissions));

                profile.getPlayer().getEffectivePermissions().clear();

                permissions.forEach(s -> {
                    profile.getPlayer().addAttachment(Origin.getInstance(), s, true);
                });

                profile.getPlayer().recalculatePermissions();
            });
        }
    }

    private Set<String> appendPermissions(Rank rank, Set<String> set) {
        if (rank == null) {
            return set;
        }

        set.addAll(rank.getPermissions());

        if (rank.getInherited() != null) {
            return appendPermissions(rank.getInherited(), set);
        }

        return set;
    }
}
