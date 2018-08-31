package eu.execom.pomodoroTeam.controllers;

import eu.execom.pomodoroTeam.entities.UserEntity;
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

    @Autowired
    public TeamController(TeamRepository teamRepository, UserRepository userRepository, TeamService teamService) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.teamService = teamService;
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
        TeamDto teamDto2 = new TeamDto();
        teamDto2.setName(team.getName());
        teamDto2.setId(team.getId());
        return new ResponseEntity<>(teamDto2, HttpStatus.CREATED);
    }

    /**
     * list of all teams
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAllTeams")
    public ResponseEntity<List<TeamEntity>> getAllTeams() {
        List<TeamEntity> teams = teamRepository.findAll();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    /**
     * get team by user
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
        TeamDto teamDto2 = new TeamDto();
        teamDto2.setName(team.getName());
        return new ResponseEntity<>(teamDto2, HttpStatus.OK);
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
        List<UserEntity> users = team.getUser();
        users.add(us);
        team.setUser(users);
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}
