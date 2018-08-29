package eu.execom.pomodoroTeam.controllers;

import eu.execom.pomodoroTeam.controllers.util.RESTError;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.dto.TeamDto;
import eu.execom.pomodoroTeam.repositories.TeamRepository;
import eu.execom.pomodoroTeam.repositories.UserRepository;

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
     * creat new team
     *
     * @param teamDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createNewTeam(@RequestBody TeamDto teamDto) {
        TeamEntity team = new TeamEntity();
        team.setName(teamDto.getName());
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    /**
     * list of all teams
     *
     * @return
     */

    @RequestMapping(method = RequestMethod.GET, value = "/getAllTeams")
    public ResponseEntity<List<TeamEntity>> getAllteam() {
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
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
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        TeamEntity team = teamRepository.getOne(id);
        if (team.getName() != null) {
            team.setName(teamDto.getName());
        }
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.OK);
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
    public ResponseEntity<?> addUserToTeam(@PathVariable Long id, @RequestParam Long user) {

        TeamEntity team = teamRepository.getOne(id);
        UserEntity us = userRepository.getOne(user);
        List<UserEntity> users = team.getUser();
        users.add(us);
        team.setUser(users);
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}







