package pl.szulc.konrad.app.gui;

import com.google.common.collect.Lists;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import pl.szulc.konrad.app.Utils;
import pl.szulc.konrad.app.HibernateSender;
import pl.szulc.konrad.app.Service;
import pl.szulc.konrad.app.runnables.LoadingTextRunnable;
import pl.szulc.konrad.app.runnables.RegisteringTextRunnable;
import pl.szulc.konrad.app.pojo_entities.Message;
import pl.szulc.konrad.app.pojo_entities.MessageDao;
import pl.szulc.konrad.app.users.UsersDao;
import pl.szulc.konrad.app.users.User;


import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main menu based on Text IO driven terminal.
 */
public class Menu {
    private static final String BEGIN_BOOKMARK = "BEGIN";
    private static final String NEW_MENU_BOOKMARK = "NEW_MENU";
    private TextIO textIO = TextIoFactory.getTextIO();
    private MessageDao messageDao = new MessageDao();
    private UsersDao usersDao = new UsersDao();
    private Service service = new Service();
    private HibernateSender hibernateSender = new HibernateSender();
    private String date = Utils.dateFormat(LocalDateTime.now());
    private Windows windows = new Windows();

    private User currentUser;
    private Message message;

    public void firstWindow() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        textIO.getTextTerminal().setBookmark(BEGIN_BOOKMARK);


        int option = 1;
        while (option > 0) {

            textIO.getTextTerminal().getProperties().setInputColor(Color.cyan);
            textIO.getTextTerminal().getProperties().setPromptColor(Color.green);

            //textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
            textTerminal.print(Lists.newArrayList(
                    "Menu:",
                    "1. Login",
                    "2. Register",
                    "3. Exit",
                    ""
            ));
            textTerminal.println();

            option = textIO.newIntInputReader()
                    .withMinVal(1)
                    .withMaxVal(3)
                    .read("Your input:");

            switch (option) {
                case 1:
                    runIt();
                    break;
                case 2:
                    addUser();
                    break;
                case 3:
                    System.exit(0);
            }
        }

    }

    public void runIt() {
        while (true) {

            textIO.getTextTerminal().setBookmark(NEW_MENU_BOOKMARK);
            currentUser = null;
            while (currentUser == null) {
                textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);
                currentUser = login();
            }
            textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);
            printHeader();
            if (currentUser.getName() != null) {
                printUserMenu();
            }
            textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);
        }
    }


    private void printHeader() {
        textIO.getTextTerminal().getProperties().setPromptColor(Color.BLUE);

        textIO.getTextTerminal().print("KCommunicator ver. 0.7 BETA" +
                " Copyright: Konrad Szulc\n");
        textIO.getTextTerminal().getProperties().setPromptColor(Color.YELLOW);
        textIO.getTextTerminal().printf("\nWelcome " + currentUser.getName() + " , date of logging in: " + date);
        textIO.getTextTerminal().setBookmark(NEW_MENU_BOOKMARK);
    }

    private void printUserMenu() {
        int option = 1;
        while (option > 0) {
            TextTerminal<?> textTerminal = textIO.getTextTerminal();

            textIO.getTextTerminal().getProperties().setPromptColor(Color.green);

            textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
            textTerminal.println();
            textTerminal.print(Lists.newArrayList(
                    "Menu:",
                    "1. Send a message",
                    "2. Show all messages",
                    "3. Show selected messages",
                    "0. Log out",
                    ""
            ));
            textTerminal.println();

            option = textIO.newIntInputReader()
                    .withMinVal(0)
                    .withMaxVal(3)
                    .read("Your input:");

            switch (option) {
                case 1:
                    addBook();
                    break;
                case 2:
                    printBooks();
                    break;
                case 3:
                    printSelectedMessages();
                    break;

            }
        }
    }


    private void addUser() {
        textIO.getTextTerminal().setBookmark(NEW_MENU_BOOKMARK);
        textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);

        RegisteringTextRunnable registeringUserRunnable = new RegisteringTextRunnable();
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
        String login;
        while (true){
            boolean leaveLoop = false;
            login = textIO.newStringInputReader()
                    .read("Login to register: ");
            for (int i = 0; i < usersDao.list().size(); i++) {
                if (login.equals(usersDao.list().get(i).getLogin()) ) {
                    leaveLoop = true;
                    break;
                }

            }
            if (leaveLoop == true) {
                textTerminal.print("Login already in use! \n");

            }
            if (leaveLoop == false) {
                break;
            }

        }

        String password = textIO.newStringInputReader()
                .read("Password: ");
        String name;

        while (true){
            boolean leaveLoop = false;
            name = textIO.newStringInputReader()
                    .read("Username: ");
            for (int i = 0; i < usersDao.list().size(); i++) {
                if (name.equals(usersDao.list().get(i).getName()) ) {
                    leaveLoop = true;
                    break;
                }

            }
            if (leaveLoop == true) {
                textTerminal.print("Username already in use! \n");

            }
            if (leaveLoop == false) {
                break;
            }

        }

        Thread thread = new Thread(registeringUserRunnable);
        thread.start();
        hibernateSender.registerUser(login, password, name);
        registeringUserRunnable.setCheck(true);
        textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);

    }

    private void printBooks() {
        List<Message> messages = messageDao.findMessages(currentUser.getName());
        printList(messages);
    }

    private void printSelectedMessages() {
        windows.printUsers(currentUser.getName());

        String receiver = textIO.newStringInputReader()
                .read("\n" + "Message receiver: ");
        List<Message> selectedMessages = messageDao.findSelectedMessages(currentUser.getName(), receiver);
        printList(selectedMessages);

    }

    private void printUsers() {

        List<User> users = usersDao.list();
        printList(users);
    }

    private void addBook() {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
        String author = currentUser.getName();
        windows.printUsers(currentUser.getName());
        String receiver;
        String content;
        boolean leaveLoop = false;
        while (true) {
            receiver = textIO.newStringInputReader()
                    .read("\n" + "Receiver: ");
            for (int i = 0; i < usersDao.list().size(); i++) {
                if (receiver.equals(usersDao.list().get(i).getName()) && !receiver.equals(currentUser.getName())) {
                    leaveLoop = true;
                    break;
                }

            }
            if (leaveLoop == false) {
                textTerminal.print("No such contact found!");

            }
            if (leaveLoop == true) {
                break;
            }


        }
        content = textIO.newStringInputReader().withMaxLength(160)
                .read("\n" + "Message Content: ");

        LoadingTextRunnable loadingTextRunnable = new LoadingTextRunnable();
        Thread thread = new Thread(loadingTextRunnable);
        thread.start();
        hibernateSender.sendAMessage(content, author, receiver, Utils.dateFormat(LocalDateTime.now()));
        loadingTextRunnable.setCheck(true);


    }


    private void printList(List<?> list) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
        List<String> messages = new ArrayList<>();
        if (list.isEmpty()) {
            messages.add("List is empty.");
        } else {
            messages.addAll(list.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()));

        }

        textTerminal.println();
        textTerminal.print(messages);
        textTerminal.println();
        textTerminal.println();
        textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(0)
                .read("Press 0 to go back");
    }


    private User login() {
        textIO.getTextTerminal().setBookmark(BEGIN_BOOKMARK);
        textIO.getTextTerminal().getProperties().setPromptColor(Color.BLUE);
        textIO.getTextTerminal().print("To go back, write and commit '0'\n");
        textIO.getTextTerminal().getProperties().setPromptColor(Color.green);
        String login = textIO.newStringInputReader()
                .read("Login");

        if (login.contains("0")) {
            textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);

            firstWindow();

        }
        String password = textIO.newStringInputReader()
                .withInputMasking(true)
                .read("Password");

        if (password.contains("0")) {
            textIO.getTextTerminal().resetToBookmark(BEGIN_BOOKMARK);

            firstWindow();
        }
        return service.findUser(login, password);
    }

    public static void main(String[] args) {
        new Menu().firstWindow();
        System.exit(0);
    }
}
