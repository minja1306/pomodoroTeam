package eu.execom.pomodoroTeam.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LoginController{
	
@RequestMapping("/user")
public Principal user(Principal principal) {
    System.out.println("Successfully found endoint");
    System.out.println("" + principal.getName());
    return principal;
}

/*@RequestMapping("/user")
public String home(Principal user) {
  return "Hello " + user.getName();
}*/
}
  /*@GetMapping
  public  ResponseEntity<?> getUserEmail(Principal principal) { 
	  Google google = new GoogleTemplate(token);
	  Person profile = google.plusOperations().getGoogleProfile();
	  String email = profile.getAccountEmail();

	  if (!email.endsWith("gmail")) {
	    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	  }
	
	  User user = userService.findByEmail(email);

	Collection<GrantedAuthority> authorities = Collections.singletonList(
	      new SimpleGrantedAuthority(user.getUserRole().name()));

	  Authentication authentication = new PreAuthenticatedAuthenticationToken(user, null, authorities);
	  SecurityContextHolder.getContext().setAuthentication(authentication);
	  
  return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
  }
  
  @GetMapping("/authentication")
  public ResponseEntity authenticate(HttpServletRequest request) {
    HttpStatus status = authenticationStatus(request);
    return new ResponseEntity(status);
  }

  private HttpStatus authenticationStatus(HttpServletRequest request) {
    Object authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication instanceof AnonymousAuthenticationToken) {
      return HttpStatus.UNAUTHORIZED;
    }
    return HttpStatus.OK;
  }
  
}
*/