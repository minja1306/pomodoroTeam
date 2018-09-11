package eu.execom.pomodoroTeam.controllers;


import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.UserDto;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import eu.execom.pomodoroTeam.services.UserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import eu.execom.pomodoroTeam.services.Mapper;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(path = "/user")
public class LoginController {

    private UserService userService;

    private Mapper mapper;

    private UserRepository userRepository;

    private static Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    public LoginController(UserService userService, Mapper mapper, UserRepository userRepository) {
        this.userService = userService;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /**
     * authentication
     *
     * @param principal
     * @return
     */
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
     * add user
     *
     * @param userDto
     * @return
     */
    @PostMapping(value = "/addUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userService.createUser(userDto);
        return new ResponseEntity<>(mapper.userToUserDto(userEntity), HttpStatus.CREATED);
    }

    /**
     * get user by id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity user = userRepository.getOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * update user
     *
     * @param id
     * @param userDto
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserEntity user = userRepository.getOne(id);
        if (user.getName() != null) {
            user.setName(userDto.getName());
        }
        if (user.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        userRepository.save(user);

        UserDto updatedUserDto = (UserDto) mapper.userToUserDto(user);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
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
}

