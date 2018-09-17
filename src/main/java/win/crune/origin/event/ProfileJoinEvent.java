package win.crune.origin.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import win.crune.origin.profile.Profile;

/**
 * Called when a profile joins the server
 */
public class ProfileJoinEvent extends ProfileEvent {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public ProfileJoinEvent(Profile profile) {
        super(profile);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
