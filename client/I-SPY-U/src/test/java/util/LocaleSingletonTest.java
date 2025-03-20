package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
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

    @Test
    void testLocaleAE(){
        System.out.println("Testing Locale US");
        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Add device => إضافة جهاز");
        assertEquals("إضافة جهاز", instance.getTranslation("add_device"));
        System.out.println("Language: " + instance.getTranslation("language"));
        assertEquals("عربي", instance.getTranslation("language"));
    }

    @Test
    void testLocalizedDateTimeLongFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));
    }

    @Test
    void testLocalizedDateTimeShortFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));
    }

    @Test
    void testLocalizedDateLongFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));

        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));
    }

    @Test
    void testLocalizedDateShortFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));
        assertEquals("30.11.2018", instance.getShortFormattedDate(date));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));
        assertEquals("11/30/18", instance.getShortFormattedDate(date));


        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));


    }

    @Test
    void testRightToLeft(){
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertFalse(instance.isRightToLeft());

        instance.setLocale(new Locale("en", "US"));
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertFalse(instance.isRightToLeft());

        instance.setLocale(new Locale("ar", "AE"));
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertTrue(instance.isRightToLeft());


    }

    @Test
    void testNumberFormat(){
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12,34", instance.getFormattedDouble(12.34));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12.34", instance.getFormattedDouble(12.34));

        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12.34", instance.getFormattedDouble(12.34));
    }

    @Test
    void testPercentageFormat(){
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50 %", instance.getFormattedPercent(0.5));


        instance.setLocale(new Locale("en", "US"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50%", instance.getFormattedPercent(0.5));

        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50\u200E%\u200E", instance.getFormattedPercent(0.5));
    }

    @Test
    void testCurrencyFormat(){
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("12,34 €", instance.getFormattedCurrency(12.34));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("$12.34", instance.getFormattedCurrency(12.34));


        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("\u200F12.34 د.إ.\u200F", instance.getFormattedCurrency(12.34));

    }

    @Test
    void testTemperatureFormat(){
        instance.setLocale(new Locale("fi", "FI"));
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("12,34°C", instance.getFormattedTemperature(12.34));

        instance.setLocale(new Locale("en", "US"));
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("54.21°F", instance.getFormattedTemperature(12.34));


        instance.setLocale(new Locale("ar", "AE"));
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("م°12.34", instance.getFormattedTemperature(12.34));
    }
}