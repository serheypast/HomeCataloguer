package ru.bsuir.lab2.sample.searchPatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Сергей on 07.03.2017.
 */
public class SearchPattern {
    /**
     * Search @param patternString in @param text
     * @param patterString pattern for search
     * @param text Search string where this pattern will be searched
     * @return return first coincidence @param patterString in @param text
     */
    public static String findStringRegular(String patterString,String text){
        Pattern pattern = Pattern.compile(patterString);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()){
            return matcher.group();
        }
        return "";
    }
}
