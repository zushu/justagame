package com.group5.service;

import com.group5.model.Score;
import com.group5.model.User;
import com.group5.repository.ScoreRepository;
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
import java.text.SimpleDateFormat;  
import java.util.Date;  

@Service
public class ScoreServiceImpl implements ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Score addScore(Score score) {
        return scoreRepository.save(score);
    }

    @Override
    public List<Score> getAllScores() {
        return this.scoreRepository.findAll();
    }

    @Override
    public List<Score> scoreboardAfterDate(String date){    //Bu fonksiyon doğrudan java.util.Date parametre de alabilir, postman kullanıldığı için string    
        try {
            Date parsedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date); 
        
            List<User> distinctUsersAfterDate = scoreRepository.distinctUsersAfterDateJpa(parsedDate);
            List<Score> scoresAfterDate = scoreRepository.scoreboardAfterDateJpa(parsedDate);

            List<Score> scoreBoard = new ArrayList<Score>();
            List<User> addedUsers = new ArrayList<User>();
            for (int i=0; i< scoresAfterDate.size(); i++){
                for(int j=0; j<distinctUsersAfterDate.size(); j++){
                    if ( !addedUsers.contains(distinctUsersAfterDate.get(j)) && distinctUsersAfterDate.get(j).getId() == scoresAfterDate.get(i).getUser().getId()){
                        scoreBoard.add(scoresAfterDate.get(i));
                        addedUsers.add(distinctUsersAfterDate.get(j));
                        break;
                    }
                }
            }
            return scoreBoard;
        } catch (Exception e) {
            System.out.println("date parse exception occured");
            e.printStackTrace();
            List<Score> test = new ArrayList<Score>();
            return test;
        }    
    }

    @Override
    public Score updateScore(Score score){
        Optional<Score> scoreDb = this.scoreRepository.findById(score.getId());
        Score scoreUpdate = scoreDb.get();

        scoreUpdate.setScore(score.getScore());
        scoreUpdate.setEndTime(score.getEndTime());
        return  this.scoreRepository.save(scoreUpdate);
    }

    @Override
    public void deleteScore(List<Integer> idList) {
        Iterable<Score> scores = this.scoreRepository.findAllById(idList);
        for(Score score : scores) this.scoreRepository.delete(score);
    }

    /*@Override
    public List<Score> findScore(JSONObject jsonScore) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Score> cq = cb.createQuery(Score.class);

        Root<Score> score = cq.from(Score.class);
        List<Predicate> predicates = new ArrayList<>();

        if (jsonScore.has("id"))
            predicates.add(cb.equal(score.get("id"), jsonScore.get("id")));
        if (jsonScore.has("name"))
            predicates.add(cb.equal(score.get("name"), jsonScore.get("name")));
        if (jsonScore.has("password"))
            predicates.add(cb.equal(score.get("password"), jsonScore.get("password")));


        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }*/
}
