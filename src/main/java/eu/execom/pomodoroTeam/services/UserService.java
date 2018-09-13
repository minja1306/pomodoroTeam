package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.Invitation;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.UserDto;
import eu.execom.pomodoroTeam.repositories.InvitationRepository;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private InvitationRepository invitationRepository;

    @Autowired
    public UserService(UserRepository userRepository, InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.invitationRepository = invitationRepository;
    }

    public UserEntity createUser( UserDto userDto) {
        UserEntity user = userRepository.getByEmail(userDto.getEmail());
        if (user == null) {
            user = new UserEntity();
            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
        }
        return userRepository.save(user);
    }

    public Invitation createInvitation(String username, Long teamId) {
        Invitation invitation = new Invitation();

        invitation.setUsername(username);
        invitation.setTeamId(teamId);
        invitation.setActivationLink(UUID.randomUUID().toString());

        return invitationRepository.save(invitation);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }
  }
