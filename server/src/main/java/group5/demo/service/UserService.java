package group5.demo.service;

import group5.demo.model.User;
import org.json.JSONObject;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(List<Integer> idList);
    List<User> findUser(JSONObject jsonUser);
}
