package win.crune.origin.profile;

import lombok.Getter;
import win.crune.origin.handler.Handler;
import win.crune.origin.store.Store;
import win.crune.origin.store.Stores;

@Getter
public class ProfileHandler implements Handler {

    private Store<Profile> profileStore;

    @Override
    public void onEnable() {
        this.profileStore = Stores.newUniqueStore();
    }

    @Override
    public void onDisable() {

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
