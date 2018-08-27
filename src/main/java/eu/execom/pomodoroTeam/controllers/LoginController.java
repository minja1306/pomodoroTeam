package eu.execom.pomodoroTeam.controllers;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.UserDto;
import eu.execom.pomodoroTeam.repositories.UserRepository;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        OAuth2Authentication details = (OAuth2Authentication) principal;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) details.getUserAuthentication();
        Map tokenDetails = (Map) token.getDetails();
        System.out.println("Successfully found endoint");
        System.out.println("" + tokenDetails.get("email") + " " + tokenDetails.get("name"));
        return principal;
    }

    @GetMapping("/user/getAll")
    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();

    }

    @PostMapping(value = "/user/addUser")
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
}