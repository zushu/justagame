package com.group5.repository;

import com.group5.model.Game;
import com.group5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Date;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("select g from Game g where g.endTime > ?1 order by g.score desc")     // 'Game' ve 'endTime' şeklinde yazılmazsa hata veriyor!!!
    List<Game> scoreboardAfterDateJpa(Date startingDate);

    @Query("select distinct g.user from Game g where g.endTime > ?1")  
    List<User> distinctUsersAfterDateJpa(Date startingDate);
}