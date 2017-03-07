package ru.bsuir.lab2.sample.gui.mainWindow;

/**
 * Created by Сергей on 03.03.2017.
 */
public enum Files {
    Music("Music"),
    Films("Films"),
    Books("Books"),
    Documents("Documents");

    private String name;

    Files(String name) {
        this.name = name;

    }


    public String getName() {
        return name;
    }
}
