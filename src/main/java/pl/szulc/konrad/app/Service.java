package pl.szulc.konrad.app;

/**
 *
 * This class keeps tools for managing messages and users. Beside this, use this class for manual modyfying database, content and general testing only.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.szulc.konrad.app.pojo_entities.MessageDao;
import pl.szulc.konrad.app.users.IUsersDao;
import pl.szulc.konrad.app.users.UsersDao;
import pl.szulc.konrad.app.users.User;

import java.sql.*;

public class Service {
    private static Logger logger = LoggerFactory.getLogger(UsersDao.class);

    private IUsersDao usersDao = DaoFactory.getUsersDao();
    private MessageDao messageDao = new MessageDao();


    public User findUser(String login, String password) {
        User user = usersDao.findUser(login, password);
        if(user == null) {
            return null;
        }

        return user;
    }


    public void deleteUser(int userId) {
        usersDao.deleteUser(userId);
    }

    public void addUser(String login, String password, String name) {
        User user = new User(login, password, name);
        usersDao.addUser(user);
    }




  //  public static void initializeDb() throws SQLException {

//        Connection connection = new ConnectionFactory("/library-database.properties").getConnection();
//
//        Statement statement = connection.createStatement();
//
//        DatabaseMetaData metaData = connection.getMetaData();
//
//        ResultSet rs = metaData.getColumns("messages", null, null, null);
//
//        while (rs.next()) {
//
//            String tableName = rs.getString("TABLE_NAME");
//            String columnName = rs.getString("COLUMN_NAME");
//            String type = rs.getString("TYPE_NAME");
//            logger.info("table: {}, column: {} - {}", tableName, columnName, type);
//        }

        //statement.executeUpdate("INSERT INTO users (login, password, name) VALUES ();");


   // }

//    public static void main(String[] args) throws SQLException{
//        initializeDb();
//    }

}
