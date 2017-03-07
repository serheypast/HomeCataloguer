package ru.bsuir.lab2.sample.dataBase.mainWindowDataBaseDriver;

import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.Test;
import ru.bsuir.lab2.sample.dataBase.DataBaseDriver;
import ru.bsuir.lab2.sample.models.CatalogFile;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 07.03.2017.
 */
public class MainWindowDataBaseDriverTest {
    @Test
    public void getCatalogFile() throws Exception {
        DataBaseDriver.Conn();
        String table = "Music";
        final ObservableList<ObservableList<CatalogFile>> data = MainWindowDataBaseDriver.getCatalogFile(table);
        Assert.assertEquals(".mp3",data.get(0).get(0).getType());
        table = "Book";
        final ObservableList<ObservableList<CatalogFile>> catalog = MainWindowDataBaseDriver.getCatalogFile(table);
        Assert.assertEquals(null,catalog);
    }

}