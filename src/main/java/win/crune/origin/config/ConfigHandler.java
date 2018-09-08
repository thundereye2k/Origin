package win.crune.origin.config;

import lombok.Getter;
import win.crune.origin.config.defaults.JsonConfig;
import win.crune.origin.handler.Handler;

@Getter
public class ConfigHandler implements Handler {

    private Config settingsConfig, langConfig;

    @Override
    public void onEnable() {
        this.settingsConfig = new JsonConfig("settings");
        this.langConfig = new JsonConfig("lang");
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getId() {
        return "config";
    }

    @Override
    public void setId(String s) {
        throw new RuntimeException("cannot change immutable id");
    }
}
