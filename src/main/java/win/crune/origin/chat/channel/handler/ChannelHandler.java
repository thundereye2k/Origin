package win.crune.origin.chat.channel.handler;

import lombok.Getter;
import win.crune.origin.chat.channel.Channel;
import win.crune.origin.chat.channel.defaults.ProfileChannel;
import win.crune.origin.handler.Handler;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

@SuppressWarnings("unchecked")
public class ChannelHandler implements Handler {

    @Getter
    private Store<Channel> channelStore;

    @Override
    public void onEnable() {
        this.channelStore = Stores.newNamedStore();
        channelStore.add(new ProfileChannel());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getId() {
        return "channel";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
