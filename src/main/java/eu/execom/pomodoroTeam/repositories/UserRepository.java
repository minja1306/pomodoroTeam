package eu.execom.pomodoroTeam.repositories;

import org.springframework.data.repository.CrudRepository;

import eu.execom.pomodoroTeam.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Long> {

}


