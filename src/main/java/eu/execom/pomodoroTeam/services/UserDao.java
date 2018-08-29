package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.UserEntity;

import java.util.List;

public interface UserDao {

    public List<UserEntity> findUserByTeam(Long id);
}
