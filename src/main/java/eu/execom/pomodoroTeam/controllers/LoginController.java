package eu.execom.pomodoroTeam.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("Successfully found endoint");
        System.out.println("" + principal.getName());
        return principal;
    }
}
