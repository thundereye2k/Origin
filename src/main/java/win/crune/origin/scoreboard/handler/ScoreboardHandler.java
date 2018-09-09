package win.crune.origin.scoreboard.handler;

import win.crune.origin.handler.Handler;
import win.crune.origin.scoreboard.thread.ScoreboardThread;

public class ScoreboardHandler implements Handler {

    private ScoreboardThread scoreboardThread;

    @Override
    public void onEnable() {
        this.scoreboardThread = new ScoreboardThread();
        scoreboardThread.start();
    }

    @Override
    public void onDisable() {
        scoreboardThread.stop();
    }

    @Override
    public String getId() {
        return "scoreboard";
    }

    @Override
    public void setId(String s) {

    }
}
