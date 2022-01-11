package junit.in.action.persistencelayer;

import junit.in.action.model.User;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class UserDaoJpaImpl implements UserDao {

    private final EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void deleteUser(Long id) {
        String jpql = "delete from User u where u.id = :id";
        entityManager.createQuery(jpql)
               .setParameter("id", id)
               .executeUpdate();
    }
}
