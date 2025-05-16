package MemoryCard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RankingManager {
    private static final String RANKING_FILE = "rankings.ser";  // 랭킹을 저장할 파일
    private static List<ScoreEntry> rankings = new ArrayList<>();

    static {
        loadRankings();  // 클래스 로딩 시 랭킹 데이터 불러오기
    }

    // 랭킹을 파일에서 불러오는 메서드
    public static void loadRankings() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RANKING_FILE))) {
            rankings = (List<ScoreEntry>) ois.readObject();  // 파일에서 객체를 읽어오기
        } catch (IOException | ClassNotFoundException e) {
            rankings = new ArrayList<>();
        }
    }

    // 랭킹을 파일에 저장하는 메서드
    public static void saveRankings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RANKING_FILE))) {
            oos.writeObject(rankings);  // 객체를 파일로 저장
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 랭킹에 새로운 기록 추가
    public static void addRanking(String playerName, int score) {
        rankings.add(new ScoreEntry(playerName, score));
        rankings.sort((e1, e2) -> Integer.compare(e1.getScore(), e2.getScore()));
        saveRankings();  // 랭킹을 파일에 저장
    }

    // 랭킹 목록 가져오기
    public static List<ScoreEntry> getRankings() {
        return rankings;
    }

    // 랭킹 초기화
    public static void resetRankings() {
        rankings.clear();
        saveRankings();  // 초기화된 랭킹을 파일에 저장
    }
}
