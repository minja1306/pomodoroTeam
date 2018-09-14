package eu.execom.pomodoroTeam.controllers;

import eu.execom.pomodoroTeam.entities.Invitation;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.InvitationDto;
import eu.execom.pomodoroTeam.entities.dto.MessageDto;
import eu.execom.pomodoroTeam.entities.dto.NewInvitationDto;
import eu.execom.pomodoroTeam.entities.dto.StatusDto;
import eu.execom.pomodoroTeam.entities.dto.TeamWithUserDto;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import eu.execom.pomodoroTeam.services.MailService;
import eu.execom.pomodoroTeam.services.Mapper;
import eu.execom.pomodoroTeam.services.TeamService;
import eu.execom.pomodoroTeam.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.dto.TeamDto;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping(path = "/team")
public class TeamController {

    private TeamRepository teamRepository;
    private TeamService teamService;
    private Mapper mapper;
    private UserService userService;
    private UserRepository userRepository;
    private MailService mailService;

    @Autowired
    public TeamController(TeamRepository teamRepository, TeamService teamService, Mapper mapper,
            UserService userService, UserRepository userRepository, MailService mailService) {
        this.teamRepository = teamRepository;
        this.teamService = teamService;
        this.mapper = mapper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
    }

    /**
     * create new team
     *
     * @param teamDto
     * @return createdTeamDto
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TeamDto> createNewTeam(@RequestBody TeamDto teamDto) {
        TeamEntity team = new TeamEntity();
        team.setName(teamDto.getName());
        teamRepository.save(team);
        TeamDto createdTeamDto = mapper.teamToTeamDto(team);
        return new ResponseEntity<>(createdTeamDto, HttpStatus.CREATED);
    }

    /**
     * list of all teams
     *
     * @return List<TeamDto>
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAllTeams")
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        List<TeamEntity> teams = teamRepository.findAll();
        return new ResponseEntity<>(mapper.teamListToTeamDtoList(teams), HttpStatus.OK);
    }

    /**
     * get team by id
     *
     * @param id
     * @return TeamDto
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        TeamEntity team = teamRepository.getOne(id);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }

    /**
     * update team
     *
     * @param id
     * @param teamDto
     * @return updatedTeamDto
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        TeamEntity team = teamService.updateTeam(id, teamDto.getName());
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }

    /**
     * delete teams
     *
     * @param id
     * @return id
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Long> deleteTeam(@PathVariable Long id) {
        teamRepository.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    /**
     * add user to team
     *
     * @param id
     * @param user
     * @return teamDto
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/user")
    public ResponseEntity<TeamDto> addUserToTeam(@PathVariable Long id, @RequestParam Long user) {
        TeamEntity team = teamRepository.getOne(id);
        UserEntity us = userRepository.getOne(user);
        teamService.addUserToTeam(us, team);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }

    /**
     * remove user from team
     *
     * @param id
     * @param
     * @return teamDto
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/removeFromTeam/{id}/email")
    public ResponseEntity<TeamDto> removeUserFromTeam(@PathVariable Long id, @RequestParam String email) {
        TeamEntity team = teamService.removeUserFromTeam(id, email);
        return new ResponseEntity<>(mapper.teamToTeamDto(team), HttpStatus.OK);
    }

    /**
     * send invitation
     *
     * @param invitationDto
     * @param id
     * @throws MessagingException
     */
    @PostMapping("/{id}/sendInvitation")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendInvitation(@RequestBody InvitationDto invitationDto, @PathVariable Long id)
            throws MessagingException {
        Invitation invitation = userService.createInvitation(invitationDto.getUserEmail(), id);
        mailService.sendInvitationMail("Bojana", invitation);
    }

    /**
     * activate user to team
     *
     * @param activationLink
     * @param statusDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/activateUserToTeam")
    public ResponseEntity<MessageDto> activateUserToTeam(@RequestParam(value = "activationLink") String activationLink,
            @RequestBody StatusDto statusDto) {

        MessageDto messageDto = new MessageDto();
        String message = "";
        if (!statusDto.isAccept()) {
            message = "invitation rejected";
        } else {
            teamService.addUserToTeamInvitation(activationLink);
            message = "user has been added";
        }
        messageDto.setMessage(message);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }
}

