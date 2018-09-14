package eu.execom.pomodoroTeam.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamWithUserDto {

    private Long teamId;
    private boolean found;
}

