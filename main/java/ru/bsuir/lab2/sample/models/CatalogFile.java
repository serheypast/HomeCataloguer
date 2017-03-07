package ru.bsuir.lab2.sample.models;

import javafx.collections.ObservableArray;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Сергей on 04.03.2017.
 */
public class CatalogFile{
    private String Path;
    private double size;
    private String type;
    private String name;

    public String getPath() {

        return Path;

    }

    public CatalogFile() {

    }

    public void setPath(String path) {
        Path = path;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        type = getType(name);
        this.name = name;
    }

    /**
     * send full name of file and with help regular expressions get type of File
     * @param name Name of File
     * @return type of file
     */
    private String getType(String name){
        String regular = "([.]\\w+)";
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(name);
        if(matcher.find()){
            return matcher.group();
        }
        return "unknown Format";
    }
}
