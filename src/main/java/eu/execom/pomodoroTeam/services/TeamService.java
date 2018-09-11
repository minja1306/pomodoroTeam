package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.Invitation;
import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.NewInvitationDto;
import eu.execom.pomodoroTeam.entities.dto.TeamWithUserDto;
import eu.execom.pomodoroTeam.repositories.InvitationRepository;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private InvitationRepository invitationRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, UserRepository userRepository,
            InvitationRepository invitationRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }

    public void removeUserFromTeam(Long teamId, String email) {
        TeamEntity team = teamRepository.getOne(teamId);
        UserEntity us = userRepository.getByEmail(email);
        List<UserEntity> users = team.getUsers();
        for (UserEntity u : users) {
            if (u.getEmail().equals(email)) {
                users.remove(us);
                teamRepository.save(team);
            }
        }
    }

    public UserEntity addUserToTeam(UserEntity user, TeamEntity team) {
        TeamEntity tm = teamRepository.getOne(team.getId());
        UserEntity us = userRepository.getOne(user.getId());
        List<UserEntity> users = team.getUsers();
        users.add(us);
        teamRepository.save(team);
        return user;
    }

    public TeamEntity updateTeam(Long id, String name) {
        TeamEntity team = teamRepository.getOne(id);
        if (team.getName() != null) {
            team.setName(name);
        }
        return teamRepository.save(team);
    }

    public Invitation getByActivationLink(String activationLink) {
        return invitationRepository.findOneByActivationLink(activationLink);
    }

    public NewInvitationDto DataFromInvitation(String activationLink) {
        Invitation invitation = getByActivationLink(activationLink);

        NewInvitationDto newInvitationDto = new NewInvitationDto();
        newInvitationDto.setEmail(invitation.getUsername());
        newInvitationDto.setTeamId(invitation.getTeamId());
        invitationRepository.delete(invitation);

        return newInvitationDto;
    }

    public TeamEntity getSingleTeam(Long id) {
        return teamRepository.getOne(id);
    }

    public boolean checkIfUserExistsInThatTeamByEmail(String email, Long teamId) {
        TeamEntity team = teamRepository.getOne(teamId);
        List<UserEntity> users = team.getUsers();
        boolean found = false;
        for (UserEntity u : users) {
            if (u.getEmail().equals(email)) {
                found = true;
            }
        }
        return found;
    }

    public TeamWithUserDto checkIfUserExistsInAnyTeam(String email, Long userId) {
        UserEntity user = userRepository.getOne(userId);
        List<TeamEntity> teams = user.getTeams();
        List<UserEntity> users = userRepository.findAll();
        TeamWithUserDto teamWithUserDto = new TeamWithUserDto();
        teamWithUserDto.setFound(false);
        teamWithUserDto.setTeamId(null);
        for (TeamEntity t : teams) {
            for (UserEntity u : users) {
                if (u.getEmail().equals(email) /*&& u.getTeams() == t.getId()*/) {
                    teamWithUserDto.setFound(true);
                    teamWithUserDto.setTeamId(t.getId());
                }
            }
        }
        return teamWithUserDto;
    }
}