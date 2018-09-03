package pl.szulc.konrad.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.szulc.konrad.app.pojo_entities.Message;
import pl.szulc.konrad.app.users.User;

public class HibernateSender {

    /**
     * Class needed to keep patterns and settings for sending new message and registering new user, using hibernate.
     */

    private static Logger logger = LoggerFactory.getLogger(HibernateSender.class);
    public boolean switchOff = false;


    public void sendAMessage(String contentToSend, String senderName, String receiverName, String messageDate){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Message message = new Message(contentToSend, senderName, receiverName, messageDate);



            logger.info("Before: {}", message);
            Integer id = (Integer) session.save(message);
            logger.info("Id: {}", id);
            logger.info("After: {}", message);




            transaction.commit();
            switchOff=true;
        }
    }

    public void registerUser(String login, String password, String userName){
        Configuration configuration = new Configuration();
        configuration.configure("hibernateUser.cfg.xml");

        try(SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(login, password, userName);



            logger.info("Before: {}", user);
            session.save(user);
            logger.info("After: {}", user);




            transaction.commit();
            switchOff=true;
        }
    }




}
