package eu.execom.pomodoroTeam.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TeamDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

}
