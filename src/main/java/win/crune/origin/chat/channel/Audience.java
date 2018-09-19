package win.crune.origin.chat.channel;

import org.bukkit.command.CommandSender;

import java.util.stream.Stream;

public interface Audience<T extends CommandSender> {

    void onMessage(T t, String message);

    Stream<T> getViewers();

    void view(T t);

    void hide(T t);

    Channel getChannel();

    void setChannel(Channel channel);

}
