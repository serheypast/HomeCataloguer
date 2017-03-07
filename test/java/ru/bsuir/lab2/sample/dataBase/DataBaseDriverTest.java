package ru.bsuir.lab2.sample.dataBase;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 07.03.2017.
 */
public class DataBaseDriverTest {
    @Test
    public void conn() throws Exception {
        DataBaseDriver.Conn();
        Assert.assertNotNull(DataBaseDriver.conn);
        Assert.assertNotNull(DataBaseDriver.statmt);
    }

}