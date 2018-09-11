package eu.execom.pomodoroTeam.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusDto {
    private String accept;

    public StatusDto(String accept) {
        this.accept = accept;
    }

}
