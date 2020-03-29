package com.group5.service;

import com.group5.model.Score;
import org.json.JSONObject;

import java.util.List;

public interface ScoreService {
    Score addScore(Score score);
    List<Score> getAllScores();
    Score updateScore(Score score);
    void deleteScore(List<Integer> idList);
    List<Score> scoreboardAfterDate(String date);
}
