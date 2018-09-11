package eu.execom.pomodoroTeam.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamWithUserDto {

    private boolean found;
    private Long teamId;

    public TeamWithUserDto(boolean found, Long teamId) {
        this.found = found;
        this.teamId = teamId;
    }
}

