package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.controllers.dto.TeamDto;
import eu.execom.pomodoroTeam.controllers.dto.UserDto;
import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapper {
    public UserDto userToUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }

    public List<UserDto> userListToUserDtoList(List<UserEntity> userList) {
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : userList) {
            userDtos.add(userToUserDto(user));
        }
        return userDtos;
    }

    public TeamDto teamToTeamDto(TeamEntity team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setId(team.getId());
        return teamDto;
    }

    public List<TeamDto> teamListToTeamDtoList(List<TeamEntity> teamList) {
        List<TeamDto> teamDtos = new ArrayList<>();
        for (TeamEntity team : teamList) {
            teamDtos.add(teamToTeamDto(team));
        }
        return teamDtos;
    }
}

