package ru.bsuir.lab2.sample.searchPatter;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Сергей on 07.03.2017.
 */
public class SearchPatternTest {
    @Test
    public void findStringRegular() throws Exception {
        String stringPattern = ".mp3";
        String text = "fasdf kasldf laskdf jksald lk ajf .  sadf s,df .,p .mp3 fasdf";
        Assert.assertEquals(stringPattern,SearchPattern.findStringRegular(stringPattern,text));
        stringPattern = ".mp3.+";
        Assert.assertEquals(".mp3 fasdf",SearchPattern.findStringRegular(stringPattern,text));
        stringPattern = "baby";
        Assert.assertEquals("",SearchPattern.findStringRegular(stringPattern,text));
    }

}