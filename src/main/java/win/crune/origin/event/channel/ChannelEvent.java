package win.crune.origin.event.channel;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import win.crune.origin.chat.channel.Channel;

public class ChannelEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    @Getter
    private final Channel channel;

    public ChannelEvent(Channel channel) {
        this.channel = channel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
