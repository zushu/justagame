package com.group5.service;

import com.group5.model.User;
import com.group5.model.Score;
import com.group5.repository.UserRepository;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.math.BigInteger;  
import java.security.NoSuchAlgorithmException; 

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    /**
     * This method creates a user in the database.
     * @param user User instance to save.
     * @return User Created user object.
     */
    @Override
    public User addUser(User user) {
        List<User> allUsers = getAllUsers();
        for(int i=0; i< allUsers.size(); i++){
            if(allUsers.get(i).getName().equals(user.getName())){
                return new User();
            }
        }
        user.setPassword(hashSha256(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * This method returns all users in the database.
     * @return User All user records.
     */
    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * This method updates a user in the database.
     * @param user User instance to update.
     * @return User Updated user object.
     */
    @Override
    public User updateUser(User user){
        Optional<User> userDb = this.userRepository.findById(user.getId());
        User userUpdate = userDb.get();

        userUpdate.setName(user.getName());
        userUpdate.setPassword(user.getPassword());
        return  this.userRepository.save(userUpdate);
    }

    /**
     * This method deletes users with ids in the idList.
     * @param idList List of user ids to remove.
     */
    @Override
    public void deleteUser(List<Integer> idList) {
        Iterable<User> users = this.userRepository.findAllById(idList);
        for(User user : users) this.userRepository.delete(user);
    }

    /**
     * This method finds users with given id or name in json form.
     * @param jsonUser User information in json form.
     * @return List<User> List of users according to given parameter.
     */
    @Override
    public List<User> findUser(JSONObject jsonUser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> user = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (jsonUser.has("id"))
            predicates.add(cb.equal(user.get("id"), jsonUser.get("id")));
        if (jsonUser.has("name"))
            predicates.add(cb.equal(user.get("name"), jsonUser.get("name")));

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

    /**
     * This method gets past scores of the user.
     * @param userId Id of some user to find its past scores.
     * @return List<Score> List of past scores of given user.
     */
    @Override
    public List<Score> getUsersScoreList(Integer userId){
        Optional<User> userDb = this.userRepository.findById(userId);
        User userUpdate = userDb.get();
        return userUpdate.getScoreList();
    }

    /**
     * This method is used for login with given user credentials.
     * @param user Name and Password for login.
     * @return Boolean true if login successful, false otherwise.
     */
    @Override
    public Boolean login(User user){
        try {
            User userRecord = findUserByName(user.getName());

            String hashedPassword = hashSha256((user.getPassword()));

            if (userRecord.getPassword().equals(hashedPassword)){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * This method is to change password of the user.
     * @param jsonPassword This json includes "userid","oldPassword", "newPassword"
     * @return Boolean true if password is changed successfully, false otherwise.
     */
    @Override
    public Boolean changePassword(JSONObject jsonPassword){
        Optional<User> userDb = this.userRepository.findById((Integer)jsonPassword.get("userid"));
        User userUpdate = userDb.get();

        String oldPassword = (String)jsonPassword.get("oldPassword");
        String oldPassHashed = hashSha256(oldPassword); 
        String newPassword = (String)jsonPassword.get("newPassword"); 
        String newPassHashed = hashSha256(newPassword); 

        if ( oldPassHashed.equals(userUpdate.getPassword()) ){
            userUpdate.setPassword(newPassHashed);
            this.userRepository.save(userUpdate);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Helper method to prepare hashed password.
     * @param toHash String that will be hashed.
     * @return String Hashed password.
     */
    private static String hashSha256(String toHash) 
    {  
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256"); 
            byte[] hashedPasswd = md.digest(toHash.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hashedPasswd);  
            StringBuilder hexString = new StringBuilder(number.toString(16));  

            while (hexString.length() < 32)  
            {  
                hexString.insert(0, '0');  
            }  
            return hexString.toString();  
        }
        catch (NoSuchAlgorithmException e) {  
            System.out.println("Exception thrown for incorrect algorithm: " + e);  
            return "Error occured.";
        }  
    } 

    /**
     * Helper method to find user via name.
     * @param name Name of the searched user.
     * @return User User with given name.
     */
    private User findUserByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> user = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(user.get("name"), name));

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList().get(0);
    }
}
