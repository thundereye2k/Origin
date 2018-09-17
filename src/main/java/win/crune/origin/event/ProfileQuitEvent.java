package win.crune.origin.event;

import lombok.Getter;
import org.bukkit.event.HandlerList;
import win.crune.origin.profile.Profile;

/**
 * Called when a profile quits the server
 */
public class ProfileQuitEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public ProfileQuitEvent(Profile profile) {
        super(profile);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
