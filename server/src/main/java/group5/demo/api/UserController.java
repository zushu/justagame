package group5.demo.api;

import group5.demo.model.User;
import group5.demo.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.addUser(user));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    @PostMapping("/find")
    public ResponseEntity<List<User>> findUser(@RequestBody String jsonUser){
        return ResponseEntity.ok().body(this.userService.findUser(new JSONObject(jsonUser)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(this.userService.getAllUsers());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody String ids){
        List<Integer> idList = new ArrayList<>();
        for(Object object : new JSONArray(ids)) idList.add(Integer.valueOf(String.valueOf(object)));
        this.userService.deleteUser(idList);
        return ResponseEntity.noContent().build();
    }

}