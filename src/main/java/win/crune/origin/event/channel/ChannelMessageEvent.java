package win.crune.origin.event.channel;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import win.crune.origin.chat.channel.Channel;
import win.crune.origin.chat.channel.Format;

public class ChannelMessageEvent extends ChannelEvent implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    @Getter
    @Setter
    private final String message;

    @Getter
    @Setter
    private final Format format;

    private boolean cancel;

    public ChannelMessageEvent(Channel channel, String message, Format format) {
        super(channel);
        this.message = message;
        this.format = format;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
