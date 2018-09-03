package pl.szulc.konrad.app.users;

import java.util.List;

public interface IUsersDao {
    User findUser(String login, String password);
    List<User> list();

    void addUser(User user);
    void deleteUser(int userId);
}
