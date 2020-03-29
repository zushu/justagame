package com.group5.repository;

import com.group5.model.Score;
import com.group5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Date;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    @Query("select g from Score g where g.endTime > ?1 order by g.score desc")     // 'Score' ve 'endTime' şeklinde yazılmazsa hata veriyor!!!
    List<Score> scoreboardAfterDateJpa(Date startingDate);

    @Query("select distinct g.user from Score g where g.endTime > ?1")  
    List<User> distinctUsersAfterDateJpa(Date startingDate);
}