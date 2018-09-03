package pl.szulc.konrad.app;

import pl.szulc.konrad.app.users.IUsersDao;
import pl.szulc.konrad.app.users.UsersDao;

public class DaoFactory {
    public static IUsersDao getUsersDao() {
        return new UsersDao();
    }

}