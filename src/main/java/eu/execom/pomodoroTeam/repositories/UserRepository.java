package eu.execom.pomodoroTeam.repositories;

import eu.execom.pomodoroTeam.entities.TeamEntity;
import eu.execom.pomodoroTeam.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity getByEmail(String email);

    List<UserEntity> findUserByTeams(TeamEntity team);
}
