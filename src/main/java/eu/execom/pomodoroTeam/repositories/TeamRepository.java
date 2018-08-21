package eu.execom.pomodoroTeam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import eu.execom.pomodoroTeam.entities.TeamEntity;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
