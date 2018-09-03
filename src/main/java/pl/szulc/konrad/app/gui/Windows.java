package pl.szulc.konrad.app.gui;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import pl.szulc.konrad.app.users.UsersDao;
import pl.szulc.konrad.app.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Windows {
    private static final String BEGIN_BOOKMARK = "BEGIN";
    private static final String NEW_MENU_BOOKMARK = "NEW_MENU";
    private UsersDao usersDao = new UsersDao();
    private TextIO textIO = TextIoFactory.getTextIO();

    private void printList(List<?> list) {
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.resetToBookmark(NEW_MENU_BOOKMARK);
        List<String> messages = new ArrayList<>();
        if (list.isEmpty()) {
            messages.add("Lista jest pusta.");
        } else {
            messages.addAll(list.stream()
                    .map(Object::toString)
                    .collect(Collectors.toList()));

        }

        textTerminal.println();
        textTerminal.print("Available contacts: \n");
        textTerminal.print(messages);
        textTerminal.println();
        textTerminal.println();

    }

    protected void printUsers(String forbidenName){

        List<User> users = usersDao.avaibleContactsList(forbidenName);
        printList(users);
    }


}

