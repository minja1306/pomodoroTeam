package eu.execom.pomodoroTeam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@Order(1000)
public class PomodoroTeamApplication {

    public static void main(String[] args) {
        SpringApplication.run(PomodoroTeamApplication.class, args);
    }

}
