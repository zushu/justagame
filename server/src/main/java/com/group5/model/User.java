package com.group5.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonIgnore;         // if we do not use, infinite loop happens 
                                                            //(source:https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion)
import java.util.List;

@Entity
@Table(name = "USER")
public class User {
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO, generator="user_gen") //if we do not specify a separate generator, all tables increment same id number
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade=CascadeType.REMOVE) // CascadeType.ALL prevents deleting game records via postman
    @JsonIgnore
    private List<Game> gameList;

    public User(){
    }

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Game> getGameList(){
        return this.gameList;
    }
}
