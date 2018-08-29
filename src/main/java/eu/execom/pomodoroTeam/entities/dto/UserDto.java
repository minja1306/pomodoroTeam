package eu.execom.pomodoroTeam.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {
  
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
}
