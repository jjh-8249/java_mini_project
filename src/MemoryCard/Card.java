package MemoryCard;

public class Card {
    private int value;
    private boolean revealed;

    public Card(int value) {
        this.value = value;
        this.revealed = false;
    }

    public int getValue() {
        return value;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void reveal() {
        revealed = true;
    }

    public void hide() {
        revealed = false;
    }
}
