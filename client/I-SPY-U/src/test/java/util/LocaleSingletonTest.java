package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocaleSingletonTest {
    static LocaleSingleton instance;

    @BeforeAll
    static void init(){
        Trace.setTraceLevel(Trace.Level.DEV);
        instance = LocaleSingleton.getInstance();
    }

    @Test
    void getInstance() {
        assertNotNull(instance);
    }

    @Test
    void setLocale() {
    }

    @Test
    void getAvailableLocales() {
        System.out.println("Testing at least one locale available");
        assertNotNull(instance.getAvailableLocales());
    }

    @Test
    void testLocaleFI(){
        System.out.println("Testing Locale FI");
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Login => Kirjaudu");
        assertEquals("Kirjaudu", instance.getTranslation("login"));
    }

    @Test
    void testLocaleUS(){
        System.out.println("Testing Locale US");
        instance.setLocale(new Locale("en", "US"));
        System.out.println("Login => Login");
        assertEquals("Login", instance.getTranslation("login"));
    }
}