package com.bq.app;

import org.junit.Test;

import java.lang.reflect.Field;

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
    public void test(){
       Test1 t1 = new Test1("hello");
        try {
            Field filed = Test1.class.getDeclaredField("aa");
            filed.setAccessible(true);
            filed.set(t1,"thakyou");
            System.out.println(t1.getAa());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}