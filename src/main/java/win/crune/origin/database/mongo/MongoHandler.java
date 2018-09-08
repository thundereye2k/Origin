package win.crune.origin.database.mongo;

import win.crune.origin.handler.Handler;

public class MongoHandler implements Handler {
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

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
