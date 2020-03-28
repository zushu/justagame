package com.group5.api;

import com.group5.model.Game;
import com.group5.service.GameService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/game")
@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/add")
    public ResponseEntity<Game> addGame(@RequestBody Game game){
        return ResponseEntity.ok().body(this.gameService.addGame(game));
    }

    @PostMapping("/update")
    public ResponseEntity<Game> updateGame(@RequestBody Game game){
        return ResponseEntity.ok().body(this.gameService.updateGame(game));
    }

    /*@PostMapping("/find")
    public ResponseEntity<List<Game>> findGame(@RequestBody String jsonGame){
        return ResponseEntity.ok().body(this.gameService.findGame(new JSONObject(jsonGame)));
    }*/

    @GetMapping("/getAll")
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok().body(this.gameService.getAllGames());
    }

    @PostMapping("/scoreboard")
    public ResponseEntity<List<Game>> scoreboardAfterDate(@RequestBody String date){
        return ResponseEntity.ok().body(this.gameService.scoreboardAfterDate(date));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGame(@RequestBody String ids){
        List<Integer> idList = new ArrayList<>();
        for(Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.gameService.deleteGame(idList);
        return ResponseEntity.noContent().build();
    }

}