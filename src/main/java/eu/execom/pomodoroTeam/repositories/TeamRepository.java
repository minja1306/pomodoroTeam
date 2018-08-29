package eu.execom.pomodoroTeam.repositories;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    TeamEntity findOneById(Long id);
}
