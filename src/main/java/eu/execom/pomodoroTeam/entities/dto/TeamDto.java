package eu.execom.pomodoroTeam.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = {"user"})
public class TeamDto {
    private Long id;
    private String name;
}
