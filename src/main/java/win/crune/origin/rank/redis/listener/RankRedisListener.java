package win.crune.origin.rank.redis.listener;

import org.bukkit.Bukkit;
import win.crune.origin.Origin;
import win.crune.origin.chat.Chat;
import win.crune.origin.database.mongo.MongoHandler;
import win.crune.origin.rank.Rank;
import win.crune.origin.rank.RankHandler;
import win.crune.origin.rank.redis.message.RankMessage;
import win.crune.redismessenger.listener.RedisListener;
import win.crune.redismessenger.listener.annotation.RedisHandler;
import win.crune.redismessenger.message.RedisMessage;

import java.util.UUID;

public class RankRedisListener implements RedisListener {

    private RankHandler rankHandler;
    private MongoHandler mongoHandler;

    public RankRedisListener() {
        this.rankHandler = (RankHandler) Origin.getInstance().getHandlerStore().get("rank");
        this.mongoHandler = (MongoHandler) Origin.getInstance().getHandlerStore().get("mongo");
    }

    @RedisHandler(name = "rank")
    public void onMessage(RedisMessage redisMessage) {
        String[] args = redisMessage.getMessage();

        int action = Integer.parseInt(args[0]);
        Rank rank = rankHandler.getRankStore().get(UUID.fromString(args[1]));

        if (action == RankMessage.CREATE) {
            if (rank == null) {
                rank = new Rank(UUID.fromString(args[0]));
            }
            mongoHandler.load(rank, "ranks");
        }

        if (action == RankMessage.UPDATE) {
            mongoHandler.load(rank, "ranks");
        }

        if (action == RankMessage.DELETE) {
            rankHandler.deleteRank(rank, mongoHandler);
        }

        String message = "&c[Rank Manager] &f" + rank.getName() + " has been &3" +
                (action == RankMessage.CREATE ? "CREATED"
                        : action == RankMessage.UPDATE ? "UPDATED"
                        : action == RankMessage.DELETE ? "DELETED"
                        : "PINGED") + "&f...";
        Bukkit.broadcast(Chat.color(message), "origin.alert.rank");
    }

}
