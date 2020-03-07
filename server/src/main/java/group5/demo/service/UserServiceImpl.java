package group5.demo.service;

import group5.demo.model.User;
import group5.demo.repository.UserRepository;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager em;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User updateUser(User user){
        Optional<User> userDb = this.userRepository.findById(user.getId());
        User userUpdate = userDb.get();

        userUpdate.setName(user.getName());
        userUpdate.setPassword(user.getPassword());
        return  this.userRepository.save(userUpdate);
    }

    @Override
    public void deleteUser(List<Integer> idList) {
        Iterable<User> users = this.userRepository.findAllById(idList);
        for(User user : users) this.userRepository.delete(user);
    }

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
        if (jsonUser.has("password"))
            predicates.add(cb.equal(user.get("password"), jsonUser.get("password")));


        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }
}
