package com.group5.service;

import com.group5.model.User;
import com.group5.model.Game;
import org.json.JSONObject;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    User updateUser(User user);
    void deleteUser(List<Integer> idList);
    List<User> findUser(JSONObject jsonUser);
    List<Game> getUsersGameList(Integer userId);

    Boolean login(User user);
    Boolean changePassword(JSONObject jsonPassword);
}
