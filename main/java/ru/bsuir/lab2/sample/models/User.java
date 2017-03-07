package ru.bsuir.lab2.sample.models;

/**
 * Created by Сергей on 04.03.2017.
 */

public class User {
    private String login;
    private String infAboutUpd;
    private int role;

    public User(){

    }

    public User(String login, String infAboutUpd, int role) {
        this.login = login;
        this.infAboutUpd = infAboutUpd;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getInfAboutUpd() {
        return infAboutUpd;
    }

    public void setInfAboutUpd(String infAboutUpd) {
        this.infAboutUpd = infAboutUpd;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
