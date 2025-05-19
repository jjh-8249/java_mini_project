package MemoryCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MemoryCardGame extends JFrame implements CardGame {
    private final int BOARD_SIZE = 4;
    private GameBoard gameBoard;
    private CardButton[] buttons;
    private int firstCard = -1;
    private int secondCard = -1;
    private int moves = 0;

    public MemoryCardGame() {
        setTitle("카드 뒤집기 게임");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        // 예외 처리 제거
        RankingManager.loadRankings();  // 예외를 던지지 않음, 예외 처리 코드 제거

        createMenuBar();
        startGame();
        setVisible(true);
    }

    // 게임 시작
    @Override
    public void startGame() {
        int totalCards = BOARD_SIZE * BOARD_SIZE;
        gameBoard = new GameBoard(totalCards);
        buttons = new CardButton[totalCards];
        moves = 0;
        getContentPane().removeAll();
        revalidate();
        repaint();

        for (int i = 0; i < totalCards; i++) {
            CardButton btn = new CardButton(i);
            buttons[i] = btn;
            add(btn);

            btn.addActionListener(e -> {
                try {
                    handleCardClick(btn.getIndex());
                } catch (InvalidCardSelectionException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "선택 오류", JOptionPane.WARNING_MESSAGE);
                }
            });
        }
    }

    private void handleCardClick(int index) throws InvalidCardSelectionException {
        Card card = gameBoard.getCard(index);
        if (card.isRevealed()) {
            throw new InvalidCardSelectionException("이미 선택된 카드를 다시 선택할 수 없습니다.");
        }

        card.reveal();
        buttons[index].setText(String.valueOf(card.getValue()));

        if (firstCard == -1) {
            firstCard = index;
        } else if (secondCard == -1) {
            if (index == firstCard) {
                throw new InvalidCardSelectionException("같은 카드를 두 번 선택할 수 없습니다.");
            }

            secondCard = index;
            moves++;

            Card first = gameBoard.getCard(firstCard);
            Card second = gameBoard.getCard(secondCard);

            if (first.getValue() == second.getValue()) {
                firstCard = -1;
                secondCard = -1;
                if (isGameWon()) {
                    handleGameWin();
                }
            } else {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        first.hide();
                        second.hide();
                        buttons[firstCard].setText(" ?");
                        buttons[secondCard].setText("?");
                        firstCard = -1;
                        secondCard = -1;
                    }
                }, 700);
            }
        }
    }

    private void handleGameWin() {
        String playerName = JOptionPane.showInputDialog(this, "축하합니다! 이름을 입력하세요:");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "이름 없음";
        }

        RankingManager.addRanking(playerName, moves);

        String message = "🎉 게임 완료! " + playerName + "님, 총 " + moves + "번 만에 성공!\n\n" +
                "🎯 현재 최상위 기록:\n";

        List<ScoreEntry> top = RankingManager.getRankings();
        for (int i = 0; i < Math.min(10, top.size()); i++) {
            message += (i + 1) + ". " + top.get(i) + "\n";
        }

        JOptionPane.showMessageDialog(this, message);

        // 게임 승리 후 재시작 또는 종료 선택 다이얼로그
        int option = JOptionPane.showOptionDialog(this, 
            "게임을 재시작 하시겠습니까?", 
            "게임 종료", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            new Object[] { "재시작", "그만하기" }, 
            "재시작");

        if (option == JOptionPane.YES_OPTION) {
            restartGame();  // 게임 초기화 및 재시작
        } else {
            System.exit(0);  // 그만하기 선택 시 프로그램 종료
        }
    }

    @Override
    public boolean isGameWon() {
        return gameBoard.allMatched();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("메뉴");
        JMenuItem viewRanking = new JMenuItem("랭킹 보기");

        viewRanking.addActionListener(e -> new RankingDialog(this).setVisible(true));
        menu.add(viewRanking);

        JMenuItem resetRanking = new JMenuItem("랭킹 초기화");
        resetRanking.addActionListener(e -> {
            RankingManager.resetRankings();
            JOptionPane.showMessageDialog(this, "랭킹이 초기화되었습니다.");
        });
        menu.add(resetRanking);

        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    // 게임 재시작 함수
    public void restartGame() {
        // 게임 보드 및 상태 초기화
        gameBoard.reset();  // GameBoard 클래스에 reset() 메소드가 필요할 수 있습니다.
        moves = 0;

        // 게임 UI 초기화
        getContentPane().removeAll();  // 화면 리셋
        revalidate();
        repaint();

        // 새 카드 배치
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            CardButton btn = new CardButton(i);
            buttons[i] = btn;
            add(btn);

            btn.addActionListener(e -> {
                try {
                    handleCardClick(btn.getIndex());
                } catch (InvalidCardSelectionException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "선택 오류", JOptionPane.WARNING_MESSAGE);
                }
            });
        }
    }
}
