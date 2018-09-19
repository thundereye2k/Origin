package win.crune.origin.chat.channel;

import win.crune.origin.store.Storeable;

import java.util.Collection;
import java.util.UUID;

public interface Channel<T extends Audience> extends Storeable<String> {

    void sendMessage(String message, UUID uuid);

    Format getFormat();

    void setFormat(Format format);

    T getAudience();

}
