package eu.execom.pomodoroTeam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import eu.execom.pomodoroTeam.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
}

