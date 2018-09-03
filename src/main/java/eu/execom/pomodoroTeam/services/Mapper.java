package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.UserEntity;
import eu.execom.pomodoroTeam.entities.dto.TeamDto;
import eu.execom.pomodoroTeam.entities.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class Mapper {
    public UserDto userToUserDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        return userDto;
    }

    public List<UserDto> userListToUserDtoList(List<UserEntity> userList) {
        List<UserDto> userDtos = userList.stream().map(this::userToUserDto).collect(Collectors.toList());
        return userDtos;
    }

    public TeamDto teamToTeamDto(TeamEntity team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setId(team.getId());
        return teamDto;
    }

    public List<TeamDto> teamListToTeamDtoList(List<TeamEntity> teamList) {
        List<TeamDto> teamDtos = teamList.stream().map(this::teamToTeamDto).collect(Collectors.toList());
        return teamDtos;
    }
}

