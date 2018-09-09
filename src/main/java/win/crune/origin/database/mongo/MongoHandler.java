package win.crune.origin.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import lombok.Getter;
import win.crune.origin.Origin;
import win.crune.origin.config.Config;
import win.crune.origin.config.ConfigHandler;
import win.crune.origin.handler.Handler;

import java.util.Collections;
import java.util.List;

@Getter
public class MongoHandler implements Handler {

    private MongoClient mongoClient;

    @Override
    public void onEnable() {
        ConfigHandler configHandler = (ConfigHandler) Origin.getInstance().getHandlerStore().get("config");
        Config settingsConfig = configHandler.getSettingsConfig();

        String host = settingsConfig.getString("databases.mongo.host");
        int port = settingsConfig.getInteger("databases.mongo.port");
        String database = settingsConfig.getString("databases.mongo.database");

        try {
            String username = settingsConfig.getString("databases.mongo.username");
            String password = settingsConfig.getString("databases.mongo.password");

            List<MongoCredential> list = Collections.singletonList(
                    MongoCredential.createCredential(username, database, password.toCharArray())
            );

            this.mongoClient = new MongoClient(new ServerAddress(host, port), list);
        } catch (Exception e) {
            this.mongoClient = new MongoClient(host, port);
        }

        mongoClient.getDatabase(database);
    }

    @Override
    public void onDisable() {
        mongoClient.close();
    }

    @Override
    public String getId() {
        return "mongo";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
