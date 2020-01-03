package com.song.check;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_length(){
        String data = "com.zjwh.android_wh_physicalfitness";
        System.out.println(data.length());
        System.out.println("7f67a85000-7f67ab8000 r--p 00000000 b3:11 573500                         /data/dalvik-cache/arm64/system@framework@gson.jar@classes.dex".length());
    }
}