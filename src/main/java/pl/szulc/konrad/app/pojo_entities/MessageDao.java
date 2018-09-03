package pl.szulc.konrad.app.pojo_entities;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.szulc.konrad.app.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    private static Logger logger = LoggerFactory.getLogger(MessageDao.class);

    public static final ConnectionFactory CONNECTION_FACTORY = new ConnectionFactory("/library-database.properties");
    private TextIO textIO = TextIoFactory.getTextIO();


    public List<Message> findMessages(String nameOfLoggedUser) {

        List<Message> messages = new ArrayList<>();
        try(Connection connection = CONNECTION_FACTORY.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE sender='"+nameOfLoggedUser+"' OR m_receiver='"+nameOfLoggedUser+"'");) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

             //   Integer id= resultSet.getInt("id");
                String content= resultSet.getString("m_content");

                String sender= resultSet.getString("sender");
               // int categoryId= resultSet.getInt("category_id");
                String receiver= resultSet.getString("m_receiver");
                String date = resultSet.getString("send_date");

                Message message = new Message(content,sender,receiver, date);
                messages.add(message);
            }



        } catch (SQLException e){
            logger.error("", e);
        }


        return messages;
    }

    public List<Message> findSelectedMessages(String senderName, String nameOfReceiver){
        List<Message> messages = new ArrayList<>();
        try(Connection connection = CONNECTION_FACTORY.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE (sender='"+senderName+"' AND m_receiver='"+nameOfReceiver+"' OR " +
                    "sender='"+nameOfReceiver+"' AND m_receiver='"+senderName+"')");) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){

                //   Integer id= resultSet.getInt("id");
                String content= resultSet.getString("m_content");

                String sender= resultSet.getString("sender");
                // int categoryId= resultSet.getInt("category_id");
                String receiver= resultSet.getString("m_receiver");
                String date = resultSet.getString("send_date");

                Message message = new Message(content,sender,receiver, date);
                messages.add(message);
            }



        } catch (SQLException e){
            logger.error("", e);
        }


        return messages;

    }



}
