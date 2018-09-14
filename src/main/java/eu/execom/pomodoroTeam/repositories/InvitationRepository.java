package eu.execom.pomodoroTeam.repositories;


import eu.execom.pomodoroTeam.entities.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findOneByActivationLink(String activationLink);
}