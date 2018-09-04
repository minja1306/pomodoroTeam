package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public TeamEntity removeUserFromTeam(Long id, Long user) {
        TeamEntity team = teamRepository.getOne(id);
        UserEntity us = userRepository.getOne(user);
        List<UserEntity> users = team.getUsers();
        users.remove(us);
        teamRepository.save(team);
        return team;
    }

    public TeamEntity addUserToTeam(Long id, Long user) {
        TeamEntity team = teamRepository.getOne(id);
        UserEntity us = userRepository.getOne(user);
        List<UserEntity> users = team.getUsers();
        users.add(us);
        teamRepository.save(team);
        return team;
    }

    public TeamEntity updateTeam(Long id, String name) {
        TeamEntity team = teamRepository.getOne(id);
        if (team.getName() != null) {
            team.setName(name);
        }
        return teamRepository.save(team);
    }
}
