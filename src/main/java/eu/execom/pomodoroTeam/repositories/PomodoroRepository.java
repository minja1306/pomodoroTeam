package eu.execom.pomodoroTeam.repositories;

import org.springframework.data.repository.CrudRepository;

import eu.execom.pomodoroTeam.entities.PomodoroEntity;

public interface PomodoroRepository extends CrudRepository<PomodoroEntity,Long> {

}
