package com.group5.service;

import com.group5.model.Game;
import org.json.JSONObject;

import java.util.List;

public interface GameService {
    Game addGame(Game game);
    List<Game> getAllGames();
    Game updateGame(Game game);
    void deleteGame(List<Integer> idList);
    //List<Game> findGame(JSONObject jsonGame);
}
