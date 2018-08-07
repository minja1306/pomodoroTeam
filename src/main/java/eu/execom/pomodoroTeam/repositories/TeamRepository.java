package eu.execom.pomodoroTeam.repositories;

import org.springframework.data.repository.CrudRepository;

import eu.execom.pomodoroTeam.entities.TeamEntity;

public interface TeamRepository extends CrudRepository<TeamEntity,Long>{

}
