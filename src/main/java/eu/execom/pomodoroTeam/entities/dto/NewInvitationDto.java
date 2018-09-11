package eu.execom.pomodoroTeam.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewInvitationDto {

    private String email;
    private Long teamId;

    public NewInvitationDto(String email, Long teamId) {
        this.email = email;
        this.teamId = teamId;
    }
}
