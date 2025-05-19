package MemoryCard;

import javax.swing.*;

public class CardButton extends JButton {
    private int index;

    public CardButton(int index) {
        this.index = index;
        setText("?");
    }

    public int getIndex() {
        return index;
    }
}
