package eu.execom.pomodoroTeam.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitationDto {

    private String userEmail;
    private Long teamId;
}