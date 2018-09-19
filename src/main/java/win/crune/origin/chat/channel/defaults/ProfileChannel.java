package win.crune.origin.chat.channel.defaults;

import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.chat.channel.Channel;
import win.crune.origin.chat.channel.Format;
import win.crune.origin.event.channel.ChannelMessageEvent;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;

import java.util.UUID;

public class ProfileChannel implements Channel<ProfileAudience> {

    private ProfileAudience profileAudience;
    private Format format;

    public ProfileChannel() {
        this.profileAudience = new ProfileAudience();
        profileAudience.setChannel(this);
        this.format = Format.DEFAULT;
    }

    @Override
    public void sendMessage(String message, UUID uuid) {
        ProfileHandler profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
        Profile profile = profileHandler.getProfileStore().get(uuid);

        ChannelMessageEvent channelMessageEvent = new ChannelMessageEvent(this, message, format);
        Bukkit.getPluginManager().callEvent(channelMessageEvent);

        if (channelMessageEvent.isCancelled()) {
            return;
        }

        format = channelMessageEvent.getFormat();

        profileAudience.getViewers().forEach(other -> {
            profileAudience.onMessage(other, format.get()
                    .replace("{prefix}", profile.getRank() == null ? "" : profile.getRank().getPrefix())
                    .replace("{player}", profile.getDisplayName())
                    .replace("{suffix}", profile.getRank() == null ? "" : profile.getRank().getSuffix())
                    .replace("{message}", channelMessageEvent.getMessage()
                    ));
        });

    }

    @Override
    public Format getFormat() {
        return format;
    }

    @Override
    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    public ProfileAudience getAudience() {
        return profileAudience;
    }

    @Override
    public String getId() {
        return "profile";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
