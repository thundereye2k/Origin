package win.crune.origin;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import win.crune.origin.chat.channel.handler.ChannelHandler;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.database.mongo.MongoHandler;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.environment.ServerHandler;
import win.crune.origin.handler.Handler;
import win.crune.origin.profile.ProfileHandler;
import win.crune.origin.rank.RankHandler;
import win.crune.origin.scoreboard.handler.ScoreboardHandler;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;
import win.crune.redismessenger.impl.SimpleRedisMessenger;
import win.crune.redismessenger.messenger.RedisMessenger;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

@Getter
public class Origin extends JavaPlugin implements OriginAPI {

    @Getter
    private static Origin instance;

    private Store<Handler> handlerStore;
    private RedisMessenger redisMessenger;
    private ExecutorService executorService;

    @Override
    public void onEnable() {
        instance = this;

        this.handlerStore = Stores.newNamedStore();
        this.redisMessenger = new SimpleRedisMessenger();
        this.executorService = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        /* Register handlers, order is very specific because some handlers depend on each other */
        Arrays.asList(
                new ConfigHandler(), new RedisHandler(), new MongoHandler(),
                new ServerHandler(), new ProfileHandler(), new RankHandler(),
                new ScoreboardHandler(), new ChannelHandler()
        ).forEach(this::registerHandler);

        /* Load ranks */
        //((RankHandler) getHandlerStore().get("rank")).loadRanks((MongoHandler) getHandlerStore().get("mongo"));
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
