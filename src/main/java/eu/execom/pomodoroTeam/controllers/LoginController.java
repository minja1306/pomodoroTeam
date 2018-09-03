package eu.execom.pomodoroTeam.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.dto.UserDto;
import eu.execom.pomodoroTeam.services.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/user")
@RestController
public class LoginController {

    private UserRepository userRepository;

    private TeamRepository teamRepository;

    @Autowired
    public LoginController(UserRepository userRepository, TeamRepository teamRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
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
        List<UserEntity> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * create user
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/addUser")
    public ResponseEntity<UserEntity> addNewUser(@RequestBody UserDto userDto) {
        UserEntity user = userRepository.getByEmail(userDto.getEmail());
        if (user == null) {
            user = new UserEntity();
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
     * delete user
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * shaw users by team
     *
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/team/{id}")
    public List<UserEntity> findUsFromTm(@PathVariable Long id) {
        TeamEntity team = teamRepository.getOne(id);
        return userRepository.findUserByTeam(team);
    }
}