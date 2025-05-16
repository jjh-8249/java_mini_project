package MemoryCard;

import java.io.Serializable;

public class ScoreEntry implements Serializable {
    private String playerName;
    private int score;

    public ScoreEntry(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return playerName + " - " + score + "번";
    }
}
