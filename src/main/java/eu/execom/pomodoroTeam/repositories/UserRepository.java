package eu.execom.pomodoroTeam.repositories;

import eu.execom.pomodoroTeam.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByEmail(String email);
}
