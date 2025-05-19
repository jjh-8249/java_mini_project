package MemoryCard;

public class GameBoard {
    private Card[] cards;

    public GameBoard(int totalCards) {
        cards = new Card[totalCards];
        initializeCards();
    }

    public Card getCard(int index) {
        return cards[index];
    }

    public boolean allMatched() {
        for (Card card : cards) {
            if (!card.isRevealed()) {
                return false;
            }
        }
        return true;
    }

    private void initializeCards() {
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(i % (cards.length / 2) + 1);  // 카드 값 배치
        }
        shuffleCards();
    }

    private void shuffleCards() {
        for (int i = 0; i < cards.length; i++) {
            int randomIndex = (int) (Math.random() * cards.length);
            Card temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    // 게임을 초기화하는 메소드
    public void reset() {
        for (Card card : cards) {
            card.hide();  // 모든 카드를 숨기기
        }
        shuffleCards();  // 카드 순서 섞기
    }
}
