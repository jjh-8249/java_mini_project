package MemoryCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RankingDialog extends JDialog {
    public RankingDialog(JFrame parent) {
        super(parent, "역대 랭킹", true);
        setSize(300, 400);
        setLocationRelativeTo(parent);  // 부모 프레임에 상대적으로 위치 설정
        setLayout(new BorderLayout());

        JTextArea rankingArea = new JTextArea();
        rankingArea.setEditable(false);
        rankingArea.setFont(new Font("Arial", Font.PLAIN, 14));

        List<ScoreEntry> rankings = RankingManager.getRankings();
        StringBuilder rankingText = new StringBuilder("🎯 역대 랭킹\n");

        // 랭킹 10위까지 표시
        for (int i = 0; i < Math.min(10, rankings.size()); i++) {
            rankingText.append((i + 1)).append(". ").append(rankings.get(i)).append("\n");
        }

        rankingArea.setText(rankingText.toString());
        JScrollPane scrollPane = new JScrollPane(rankingArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("닫기");
        closeButton.addActionListener(e -> dispose());  // 창 닫기 버튼
        add(closeButton, BorderLayout.SOUTH);
    }
}
