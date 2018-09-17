package win.crune.origin.rank;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;
import win.crune.origin.Origin;
import win.crune.origin.database.mongo.MongoHandler;
import win.crune.origin.database.redis.RedisHandler;
import win.crune.origin.handler.Handler;
import win.crune.origin.rank.redis.listener.RankRedisListener;
import win.crune.origin.rank.thread.PermissionThread;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

import java.util.UUID;

@Getter
public class RankHandler implements Handler {

    private Store<Rank> rankStore;
    private Rank defaultRank;
    private PermissionThread permissionThread;

    @Override
    public void onEnable() {
        this.rankStore = Stores.newUniqueStore();
        this.permissionThread = new PermissionThread();
        permissionThread.start();

        RedisHandler redisHandler = (RedisHandler) Origin.getInstance().getHandlerStore().get("redis");
        redisHandler.getRedisMessenger().registerMessages(new RankRedisListener());
    }

    @Override
    public void onDisable() {
        permissionThread.stop();
    }

    @Override
    public String getId() {
        return "rank";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }

    public void setDefaultRank(Rank defaultRank) {
        if (this.defaultRank != null) {
            //TODO apply changes for the old defaultrank
        }
        this.defaultRank = defaultRank;
    }

    public void deleteRank(Rank rank, MongoHandler mongoHandler) {
        MongoCollection<Document> collection = mongoHandler.getMongoDatabase().getCollection("ranks");
        Document document = collection.find(Filters.eq("uuid", rank.getId().toString())).first();

        rankStore.remove(rank);

        if (document == null) {
            return;
        }

        collection.deleteOne(document);
        rank.setId(null);
    }

    public void loadRanks(MongoHandler mongoHandler) {
        MongoCollection<Document> collection = mongoHandler.getMongoDatabase().getCollection("ranks");

        collection.find().forEach((Block<? super Document>) document -> {
            Rank rank = rankStore.get(UUID.fromString(document.getString("uuid")));

            if (rank == null) {
                rank = new Rank(UUID.fromString(document.getString("uuid")));
                rankStore.add(rank);
            }

            mongoHandler.load(rank, "ranks");
        });
    }
}
