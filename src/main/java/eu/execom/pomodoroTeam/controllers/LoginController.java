package eu.execom.pomodoroTeam.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import eu.execom.pomodoroTeam.entities.dto.UserDto;
import eu.execom.pomodoroTeam.services.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.repositories.UserRepository;

@RequestMapping(path = "/user")
@RestController
public class LoginController {

    private UserRepository userRepository;

    private UserDao userdao;

    @Autowired
    public LoginController(UserRepository userRepository, UserDao userdao) {
        this.userRepository = userRepository;
        this.userdao = userdao;
    }

    @RequestMapping
    public Principal user(Principal principal) {
        OAuth2Authentication details = (OAuth2Authentication) principal;
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) details.getUserAuthentication();
        Map tokenDetails = (Map) token.getDetails();
        System.out.println("Successfully found endoint");
        System.out.println("" + tokenDetails.get("email") + " " + tokenDetails.get("name"));
        return principal;
    }

    /**
     * list of all users
     *
     * @return
     */
    @GetMapping("/getAll")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * create user
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/addUser")
    public ResponseEntity<?> addNewUser(@RequestBody UserDto userDto) {
        UserEntity user = userRepository.getByEmail(userDto.getEmail());
        if (user == null) {
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            userRepository.save(user);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * get user by id
     *
     * @param id
     * @return
     */

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity user = userRepository.getOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * edit user
     *
     * @param id
     * @param userDto
     * @return
     */

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {

        UserEntity user = userRepository.getOne(id);
        if (user.getName() != null) {
            user.setName(userDto.getName());
        }
        if (user.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * shaw users by team
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/team/{id}")
    public List<UserEntity> usersFromTeam(@PathVariable Long id) {
        return userdao.findUserByTeam(id);
    }

}

