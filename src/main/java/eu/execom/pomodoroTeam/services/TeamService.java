package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public void removeUserFromTeam(Long id, Long user) {
        TeamEntity team = teamRepository.getOne(id);
        UserEntity us = userRepository.getOne(user);
        List<UserEntity> users = team.getUsers();
        users.remove(us);
        teamRepository.save(team);
    }

}
