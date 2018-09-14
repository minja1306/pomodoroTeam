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

    public TeamEntity removeUserFromTeam(Long teamId, String email) {
        TeamEntity team = teamRepository.getOne(teamId);
        List<UserEntity> users = team.getUsers();
        UserEntity userToRemove = null;
        for (UserEntity user : users) {
            if (user.getEmail().equals(email)) {
                userToRemove = user;
                break;
            }
        }
        users.remove(userToRemove);
        teamRepository.save(team);
        return team;
    }

    public UserEntity addUserToTeam(UserEntity user, TeamEntity team) {
        UserEntity userEntity = userRepository.getOne(user.getId());
        List<UserEntity> users = team.getUsers();
        users.add(userEntity);
        teamRepository.save(team);
        return user;
    }

    public void addUserToTeamInvitation(String activationLink) {
        NewInvitationDto newInvitationDto = getInvitationData(activationLink);
        if (!checkIfUserExistsInThatTeamByEmail(newInvitationDto.getEmail(), newInvitationDto.getTeamId())) {
            TeamWithUserDto teamWithUserDto = checkIfUserExistsInAnyTeam(newInvitationDto.getEmail(),
                    newInvitationDto.getTeamId());
            UserEntity userEntity = userRepository.getByEmail(newInvitationDto.getEmail());
            TeamEntity teamEntity = teamRepository.getOne(newInvitationDto.getTeamId());
            List<UserEntity> users = teamEntity.getUsers();
            users.add(userEntity);
            teamRepository.save(teamEntity);
        }

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

    public NewInvitationDto getInvitationData(String activationLink) {
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