package eu.execom.pomodoroTeam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import eu.execom.pomodoroTeam.entities.PomodoroEntity;

public interface PomodoroRepository extends JpaRepository<PomodoroEntity, Long> {
}
