package win.crune.origin.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import win.crune.origin.profile.Profile;

public class ProfileEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private Profile profile;

    public ProfileEvent(Profile profile) {
        this.profile = profile;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
