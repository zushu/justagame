package com.group5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.GenerationType;
import java.util.Date;



@Entity
@Table(name = "GAME")
public class Game {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO, generator="game_gen") //if we do not specify a separate generator, all tables increment same id number
    @Column(name = "GAMEID")
    private int id;

    @Column(name = "SCORE")
    private long score;
  
    @Column(name = "ENDTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date endTime;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "USERID", referencedColumnName="ID", nullable = false)
    private User user;

    public Game(){
    }

    public Game(int id, long score, java.sql.Date endTime, User user) {
        this.id = id;
        this.score = score;
        this.endTime = endTime;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
