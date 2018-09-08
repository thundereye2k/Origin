package win.crune.origin;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.database.mongo.MongoHandler;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.handler.Handler;
import win.crune.origin.profile.ProfileHandler;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;
import win.crune.redismessenger.impl.SimpleRedisMessenger;
import win.crune.redismessenger.messenger.RedisMessenger;

import java.util.Arrays;

@Getter
public class Origin extends JavaPlugin implements OriginAPI {

    @Getter
    private static Origin instance;

    private Store<Handler> handlerStore;
    private RedisMessenger redisMessenger;

    @Override
    public void onEnable() {
        instance = this;

        this.handlerStore = Stores.newNamedStore();
        this.redisMessenger = new SimpleRedisMessenger();

        Arrays.asList(
                new ConfigHandler(), new ProfileHandler(),
                new RedisHandler(), new MongoHandler()
        ).forEach(this::registerHandler);
    }

    @Override
    public void onDisable() {
        handlerStore.getAll().forEach(this::unregisterHandler);
    }

    @Override
    public void registerHandler(Handler handler) {
        this.handlerStore.add(handler);
        handler.onEnable();
    }

    @Override
    public void unregisterHandler(Handler handler) {
        this.handlerStore.remove(handler);
        handler.onDisable();
    }


}
