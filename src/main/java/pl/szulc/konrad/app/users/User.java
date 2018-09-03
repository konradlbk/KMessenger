package pl.szulc.konrad.app.users;

public class User {
   private Integer id;
    private String login;
    private String password;
    private String name;

    public User(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }



    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {

        return String.format(name);
    }
}
