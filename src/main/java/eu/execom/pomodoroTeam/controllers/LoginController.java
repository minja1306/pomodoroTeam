package eu.execom.pomodoroTeam.controllers;

import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.UserDto;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping
    public Principal user(Principal principal) {
        OAuth2Authentication details = (OAuth2Authentication) principal;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) details.getUserAuthentication();
        Map tokenDetails = (Map) token.getDetails();
        System.out.println("Successfully found endoint");
        System.out.println("" + tokenDetails.get("email") + " " + tokenDetails.get("name"));
        return principal;
    }

    @GetMapping("/getAll")
    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addNewUser(@RequestBody UserDto userDto) {
        UserEntity user = userRepository.getByEmail(userDto.getEmail());
        if (user == null) {
            UserEntity ue = new UserEntity();
            ue.setName(userDto.getName());
            ue.setEmail(userDto.getEmail());
            userRepository.save(ue);
            return new ResponseEntity<>(ue, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Iterable<UserEntity> user = getAllUsers();
        for (UserEntity userEntity : user) {
            if (userEntity.getId().equals(id)) {
                return new ResponseEntity<>(userEntity, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email) {
        Iterable<UserEntity> user = getAllUsers();
        for (UserEntity userEntity : user) {
            if (userEntity.getId().equals(id)) {
                UserEntity ue = userRepository.getOne(id);
                if (name != null) {
                    ue.setName(name);
                }
                if (email != null) {
                    ue.setEmail(email);
                }
                userRepository.save(ue);
                return new ResponseEntity<>(ue, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}







