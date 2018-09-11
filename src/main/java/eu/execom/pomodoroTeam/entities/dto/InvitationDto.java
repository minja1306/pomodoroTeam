package eu.execom.pomodoroTeam.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvitationDto {

    private String userEmail;
    private Long id;

    public InvitationDto(String userEmail, Long id) {
        this.userEmail = userEmail;
        this.id = id;
    }
}