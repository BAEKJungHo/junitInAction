package junit.in.action.persistencelayer;

import junit.in.action.model.User;

public interface UserDao {

    void addUser(User user);

    User getUserById(Long id);

    void deleteUser(Long id);
}
