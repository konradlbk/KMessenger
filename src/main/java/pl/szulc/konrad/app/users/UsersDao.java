package pl.szulc.konrad.app.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.szulc.konrad.app.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao implements IUsersDao {

    private static Logger logger = LoggerFactory.getLogger(UsersDao.class);
    public static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory("/library-database.properties");


    @Override
    public User findUser(String login, String password) {
        try (Connection connection = CONNECTION_FACTORY.getConnection();
             Statement statement = connection.createStatement()) {
            String format = String.format("SELECT id, login, password, name FROM users WHERE login='%s' AND password='%s';", login, password);
            ResultSet resultSet = statement.executeQuery(format);
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                login = resultSet.getString("login");
                password = resultSet.getString("password");
                String name = resultSet.getString("name");
                return new User(login, password, name);
            }
            return null;
        } catch (SQLException e) {
            logger.error("", e);
        }

        return null;
    }


    @Override
    public List<User> list() {
        List<User> users = new ArrayList<>();
        try(Connection connection = CONNECTION_FACTORY.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

               int id= resultSet.getInt("id");
               String login= resultSet.getString("login");
               String password= resultSet.getString("password");
               String name= resultSet.getString("name");

                User user = new User(login, password, name);
                users.add(user);
            }



        } catch (SQLException e){
            logger.error("", e);
        }


        return users;
    }

    public List<User> avaibleContactsList(String forbiddenName){
        List<User> users = new ArrayList<>();
        try(Connection connection = CONNECTION_FACTORY.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE name <> '"+forbiddenName+"'");) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                int id= resultSet.getInt("id");
                String login= resultSet.getString("login");
                String password= resultSet.getString("password");
                String name= resultSet.getString("name");

                User user = new User(login, password, name);
                users.add(user);
            }



        } catch (SQLException e){
            logger.error("", e);
        }


        return users;

    }

    @Override
    public void addUser(User user) {
        String login=user.getLogin();
        String password=user.getPassword();
        String name=user.getName();
        try (Connection connection = CONNECTION_FACTORY.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO users (login, password, name) VALUES" +
                "(?, ?, ?)")){

            statement.setString(1, login);
            statement.setString(2, password);
            statement.setString(3, name);
            statement.executeUpdate();

        }catch (SQLException e){
            logger.error("", e);
        }


    }

    @Override
    public void deleteUser(int userId) {
        int id=userId;
        try (Connection connection = CONNECTION_FACTORY.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id=?")){
            statement.setInt(1, id);
            statement.executeUpdate();

        }catch (SQLException e){
            logger.error("", e);
        }
    }
}