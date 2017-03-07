package ru.bsuir.lab2.sample.dataBase.authenticationDataBaseDriver;

import org.junit.Assert;
import org.junit.Test;
import ru.bsuir.lab2.sample.dataBase.DataBaseDriver;
import ru.bsuir.lab2.sample.encrypt.EncryptPassword;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 07.03.2017.
 */
public class AuthenticationDataBaseDriverTest {
    @Test
    public void checkUser() throws Exception {
        DataBaseDriver.Conn();
        String login = "Admin";
        String password = "Admin";
        Assert.assertNotNull(AuthenticationDataBaseDriver.checkUser(login, password));

        login = "Admin";
        password = "Adminn";
        Assert.assertNull(AuthenticationDataBaseDriver.checkUser(login,password));
    }

    @Test
    public void checkLogin() throws Exception {
        DataBaseDriver.Conn();
        String login = "sergeypast";
        Assert.assertEquals(AuthenticationDataBaseDriver.checkLogin(login),false);
        login = "nezachet";
        Assert.assertEquals(AuthenticationDataBaseDriver.checkLogin(login),true);

    }

}