package win.crune.origin.profile.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import win.crune.origin.Origin;
import win.crune.origin.chat.channel.Audience;
import win.crune.origin.chat.channel.Channel;
import win.crune.origin.chat.channel.defaults.ProfileAudience;
import win.crune.origin.chat.channel.defaults.ProfileChannel;
import win.crune.origin.chat.channel.handler.ChannelHandler;
import win.crune.origin.database.mongo.MongoHandler;
import win.crune.origin.event.profile.ProfileJoinEvent;
import win.crune.origin.event.profile.ProfileQuitEvent;
import win.crune.origin.profile.Profile;
import win.crune.origin.profile.ProfileHandler;
import win.crune.origin.scoreboard.sidebar.Sidebar;

public class PlayerListener implements Listener {

    private ProfileHandler profileHandler;
    private MongoHandler mongoHandler;

    public PlayerListener() {
        this.profileHandler = (ProfileHandler) Origin.getInstance().getHandlerStore().get("profile");
        this.mongoHandler = (MongoHandler) Origin.getInstance().getHandlerStore().get("mongo");
    }

    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent event) {
        Profile profile = profileHandler.getProfileStore().get(event.getUniqueId());

        if (profile == null) {
            profile = new Profile(event.getUniqueId());
            profileHandler.getProfileStore().add(profile);
        }

        mongoHandler.load(profile, "profiles");
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Profile profile = profileHandler.getProfileStore().get(player.getUniqueId());
        profile.setSidebar(new Sidebar(player));
        profile.setName(player.getName());

        ProfileJoinEvent profileJoinEvent = new ProfileJoinEvent(profile);
        Bukkit.getPluginManager().callEvent(profileJoinEvent);
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Profile profile = profileHandler.getProfileStore().get(player.getUniqueId());

        ProfileQuitEvent profileQuitEvent = new ProfileQuitEvent(profile);
        Bukkit.getPluginManager().callEvent(profileQuitEvent);

        profileHandler.getProfileStore().remove(profile);

        Origin.getInstance().getExecutorService().submit(() -> {
            mongoHandler.save(profile, "profiles");
        });
    }

    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Profile profile = profileHandler.getProfileStore().get(player.getUniqueId());

        ChannelHandler channelHandler = (ChannelHandler) Origin.getInstance().getHandlerStore().get("channel");
        ProfileChannel profileChannel = (ProfileChannel) channelHandler.getChannelStore().get("profile");

        event.setCancelled(true);

        Audience<Profile> audience = profile.getAudience();
        if (audience == null) {
            audience = profileChannel.getAudience();
            audience.view(profile);
            return;
        }

        Channel channel = audience.getChannel();
        if (channel == null) {
            return;
        }

        channel.sendMessage(event.getMessage(), player.getUniqueId());
    }

}
