package win.crune.origin.event;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import win.crune.origin.profile.Profile;

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
