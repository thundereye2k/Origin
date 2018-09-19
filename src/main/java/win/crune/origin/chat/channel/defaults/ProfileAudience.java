package win.crune.origin.chat.channel.defaults;

import org.bukkit.ChatColor;
import win.crune.origin.Origin;
import win.crune.origin.chat.channel.Audience;
import win.crune.origin.chat.channel.Channel;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;

import java.util.stream.Stream;

public class ProfileAudience implements Audience<Profile> {

    private Channel channel;

    @Override
    public void onMessage(Profile profile, String message) {
        profile.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public Stream<Profile> getViewers() {
        ProfileHandler profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");

        return profileHandler.getProfileStore().getAll().stream().filter(profile -> profile.getAudience().equals(this));
    }

    @Override
    public void view(Profile profile) {
        profile.setAudience(this);
    }

    @Override
    public void hide(Profile profile) {
        profile.setAudience(null);
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
