package com.group5.service;

import com.group5.model.Game;
import com.group5.repository.GameRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    // TODO: getAllGames between two timestamps (for distinct users)
    @Override
    public List<Game> getAllGames() {
        return this.gameRepository.findAll();
    }

    @Override
    public List<Game> getAllGamesAfterDate(String date){
        return gameRepository.findGamesAfterDate22(date);
        //String query = "SELECT * FROM Game  WHERE Game.endDate > ?1";
        //Query q = em.createQuery(query).setParameter(1, date, TemporalType.TIMESTAMP);//.setParameter(2, endDate, TemporalType.DATE);
    }

    @Override
    public Game updateGame(Game game){
        Optional<Game> gameDb = this.gameRepository.findById(game.getId());
        Game gameUpdate = gameDb.get();

        gameUpdate.setScore(game.getScore());
        gameUpdate.setEndTime(game.getEndTime());
        return  this.gameRepository.save(gameUpdate);
    }

    @Override
    public void deleteGame(List<Integer> idList) {
        Iterable<Game> games = this.gameRepository.findAllById(idList);
        for(Game game : games) this.gameRepository.delete(game);
    }

    /*@Override
    public List<Game> findGame(JSONObject jsonGame) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Game> cq = cb.createQuery(Game.class);

        Root<Game> game = cq.from(Game.class);
        List<Predicate> predicates = new ArrayList<>();

        if (jsonGame.has("id"))
            predicates.add(cb.equal(game.get("id"), jsonGame.get("id")));
        if (jsonGame.has("name"))
            predicates.add(cb.equal(game.get("name"), jsonGame.get("name")));
        if (jsonGame.has("password"))
            predicates.add(cb.equal(game.get("password"), jsonGame.get("password")));


        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }*/
}
