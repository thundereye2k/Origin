package win.crune.origin.profile;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import win.crune.origin.Origin;
import win.crune.origin.chat.channel.Audience;
import win.crune.origin.database.mongo.Mongoable;
import win.crune.origin.profile.setting.Setting;
import win.crune.origin.rank.Rank;
import win.crune.origin.rank.RankHandler;
import win.crune.origin.scoreboard.sidebar.Sidebar;
import win.crune.origin.scoreboard.sidebar.provider.SidebarProvider;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class Profile implements Mongoable, CommandSender {

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private Rank rank;

    @Getter
    @Setter
    private Set<String> permissions;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String displayName;

    @Getter
    @Setter
    private SidebarProvider sidebarProvider;

    @Getter
    @Setter
    private Store<Setting> settingStore;

    @Getter
    @Setter
    private Sidebar sidebar;

    @Getter
    @Setter
    private boolean online;

    @Getter
    @Setter
    private Audience<Profile> audience;

    public Profile(UUID uuid) {
        this.uuid = uuid;
        this.settingStore = Stores.newNamedStore();
        settingStore.add(new Setting("sidebar", true, "Do you want to see your sidebar?"));
        this.permissions = Sets.newHashSet();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public Document toDocument() {
        Document toReturn = new Document();

        toReturn.append("rank", rank.getId().toString());
        toReturn.append("permissions", new ArrayList<>(permissions).toString());

        Document settings = new Document();
        getSettingStore().getAll().forEach(setting -> {
            settings.append(setting.getId(), setting.isEnabled() + "%\\.%" + setting.getDescription());
        });

        toReturn.append("settings", settings);
        return toReturn;
    }

    @Override
    public void fromDocument(Document document) {
        RankHandler rankHandler = (RankHandler) Origin.getInstance().getHandlerStore().get("rank");
        this.rank = rankHandler.getRankStore().get(UUID.fromString(document.getString("rank")));

        String permissions = document.getString("permissions");
        String[] array = permissions.replace("[", "").replace("]", "").split(",");
        this.permissions.addAll(Arrays.asList(array));

        Document settings = (Document) document.get("settings");
        settings.forEach((string, object) -> {
            Setting setting = settingStore.get(string);
            String[] values = ((String) object).split("%\\.%");

            if (setting == null) {
                setting = new Setting(string, Boolean.valueOf(values[0]), values[1]);
                settingStore.add(setting);
            }

            setting.setEnabled(Boolean.valueOf(values[0]));
            setting.setDescription(values[1]);
        });
    }

    @Override
    public void sendMessage(String message) {
        getPlayer().sendMessage(message);
    }

    @Override
    public void sendMessage(String[] messages) {
        getPlayer().sendMessage(messages);
    }

    @Override
    public Server getServer() {
        return getPlayer().getServer();
    }

    @Override
    public boolean isPermissionSet(String name) {
        return getPlayer().isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return getPlayer().isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(String name) {
        return getPlayer().hasPermission(name);
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return getPlayer().hasPermission(perm);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return getPlayer().addAttachment(plugin, name, value);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return getPlayer().addAttachment(plugin);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return getPlayer().addAttachment(plugin, name, value, ticks);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return getPlayer().addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        getPlayer().removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        getPlayer().recalculatePermissions();
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return getPlayer().getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return getPlayer().isOp();
    }

    @Override
    public void setOp(boolean value) {
        getPlayer().setOp(value);
    }
}
