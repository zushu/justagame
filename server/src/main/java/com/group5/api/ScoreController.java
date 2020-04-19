package com.group5.api;

import com.group5.model.Score;
import com.group5.service.ScoreService;
import com.group5.service.UserService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/score")
@RestController
public class ScoreController {
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<Score> addScore(@RequestBody Score score){
        return ResponseEntity.ok().body(this.scoreService.addScore(score));
    }

    @PostMapping("/update")
    public ResponseEntity<Score> updateScore(@RequestBody Score score){
        return ResponseEntity.ok().body(this.scoreService.updateScore(score));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Score>> getAllScores(){
        return ResponseEntity.ok().body(this.scoreService.getAllScores());
    }

    @PostMapping("/scoreboard")
    public ResponseEntity<List<Score>> scoreboardAfterDate(@RequestBody String date){
        return ResponseEntity.ok().body(this.scoreService.scoreboardAfterDate(date));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteScore(@RequestBody String ids){
        List<Integer> idList = new ArrayList<>();
        for(Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.scoreService.deleteScore(idList);
        return ResponseEntity.noContent().build();
    }

}