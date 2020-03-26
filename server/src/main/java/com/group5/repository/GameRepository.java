package com.group5.repository;

import com.group5.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("select g from Game g where g.endTime > ?1")     // 'Game' şeklinde yazılmazsa hata veriyor!!!
                                                            // 'endTime' şeklinde yazılmazsa hata veriyor!!!
    List<Game> findGamesAfterDate22(String startingDate);

}
