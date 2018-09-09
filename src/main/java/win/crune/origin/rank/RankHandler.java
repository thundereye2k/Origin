package win.crune.origin.rank;

import lombok.Getter;
import win.crune.origin.handler.Handler;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

@Getter
public class RankHandler implements Handler {

    private Store<Rank> rankStore;
    private Rank defaultRank;

    @Override
    public void onEnable() {
        this.rankStore = Stores.newUniqueStore();
    }

    @Override
    public void onDisable() {

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
}
