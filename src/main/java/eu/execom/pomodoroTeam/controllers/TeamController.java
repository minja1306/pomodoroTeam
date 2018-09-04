package eu.execom.pomodoroTeam.controllers;

import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.services.Mapper;
import eu.execom.pomodoroTeam.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.dto.TeamDto;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import eu.execom.pomodoroTeam.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/team")
public class TeamController {

    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private TeamService teamService;
    private Mapper mapper;

    @Autowired
    public TeamController(TeamRepository teamRepository, UserRepository userRepository, TeamService teamService,
            Mapper mapper) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamService = teamService;
        this.mapper = mapper;
    }

    /**
     * create new team
     *
     * @param teamDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<TeamDto> createNewTeam(@RequestBody TeamDto teamDto) {
        TeamEntity team = new TeamEntity();
        team.setName(teamDto.getName());
        teamRepository.save(team);
        TeamDto createdTeamDto = new TeamDto();
        createdTeamDto.setName(team.getName());
        createdTeamDto.setId(team.getId());
        return new ResponseEntity<>(createdTeamDto, HttpStatus.CREATED);
    }

    /**
     * list of all teams
     *
     * @return
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
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        TeamEntity team = teamRepository.getOne(id);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    /**
     * update team
     *
     * @param id
     * @param teamDto
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        TeamEntity team = teamRepository.getOne(id);
        if (team.getName() != null) {
            team.setName(teamDto.getName());
        }
        teamRepository.save(team);
        TeamDto updatedTeamDto = mapper.teamToTeamDto(team);
        return new ResponseEntity(updatedTeamDto, HttpStatus.OK);
    }

    /**
     * delete teams
     *
     * @param id
     * @return
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
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/user")
    public ResponseEntity<TeamEntity> addUserToTeam(@PathVariable Long id, @RequestParam Long user) {
        TeamEntity team = teamRepository.getOne(id);
        UserEntity us = userRepository.getOne(user);
        List<UserEntity> users = team.getUsers();
        users.add(us);
        team.setUsers(users);
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    /**
     * remove user from team
     *
     * @param id
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/removeFromTeam/{id}/user")
    public ResponseEntity<TeamEntity> removeUserFromTeam(@PathVariable Long id, @RequestParam Long user) {
        TeamEntity team = teamRepository.getOne(id);
        teamService.removeUserFromTeam(id, user);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}
