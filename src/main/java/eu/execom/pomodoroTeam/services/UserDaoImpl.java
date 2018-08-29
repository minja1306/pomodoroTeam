package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.UserEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<UserEntity> findUserByTeam(Long id) {
        String sql = "select a " + "from UserEntity a " + "left join fetch a.team u " + "where u.id= :id  ";

        Query query = em.createQuery(sql);
        query.setParameter("id", id);

        List<UserEntity> result = new ArrayList<UserEntity>();
        result = query.getResultList();
        return result;
    }
}
