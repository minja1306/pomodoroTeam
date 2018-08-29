package eu.execom.pomodoroTeam.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TeamDto {

    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Long id;
   
}
