package util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LocaleSingletonTest {
    static LocaleSingleton instance;
    static Locale japan;
    static Locale china;
    static Locale finland;
    static Locale sweden;
    static Locale us;
    static Locale arab;






    @BeforeAll
    static void init(){
        Trace.setTraceLevel(Trace.Level.DEV);
        instance = LocaleSingleton.getInstance();
        japan = Locale.JAPAN;
        china = Locale.CHINA;
        finland = Locale.of("fi", "FI");
        sweden = Locale.of("sv", "SE");
        us = Locale.of("en", "US");
        arab = Locale.of("ar", "AE");
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
        instance.setLocale(finland);
        System.out.println("Login => Kirjaudu");
        assertEquals("Kirjaudu", instance.getTranslation("login"));
    }

    @Test
    void testLocaleUS(){
        System.out.println("Testing Locale US");
        instance.setLocale(us);
        System.out.println("Login => Login");
        assertEquals("Login", instance.getTranslation("login"));
    }

    @Test
    void testLocaleAE(){
        System.out.println("Testing Locale US");
        instance.setLocale(arab);
        System.out.println("Add device => إضافة جهاز");
        assertEquals("إضافة جهاز", instance.getTranslation("add_device"));
        System.out.println("Language: " + instance.getTranslation("language"));
        assertEquals("عربي", instance.getTranslation("language"));
    }

    @Test
    void testLocalizedDateTimeLongFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(finland);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(us);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(arab);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(china);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(japan);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));

        instance.setLocale(sweden);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDateTime(date));
    }

    @Test
    void testLocalizedDateTimeShortFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(finland);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(us);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(arab);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(china);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(japan);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));

        instance.setLocale(sweden);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDateTime(date));
    }

    @Test
    void testLocalizedDateLongFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(finland);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));

        instance.setLocale(us);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));

        instance.setLocale(arab);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));

        instance.setLocale(china);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));

        instance.setLocale(japan);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getLongFormattedDate(date));
    }

    @Test
    void testLocalizedDateShortFormat(){
        Date date = Date.from(Instant.parse("2018-11-30T18:35:00.00Z"));
        instance.setLocale(finland);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));
        assertEquals("30.11.2018", instance.getShortFormattedDate(date));

        instance.setLocale(us);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));
        assertEquals("11/30/18", instance.getShortFormattedDate(date));

        instance.setLocale(arab);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));

        instance.setLocale(japan);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));

        instance.setLocale(china);
        System.out.println("Date in format: " + instance.getLocale().getDisplayCountry());
        System.out.println(instance.getShortFormattedDate(date));
    }

    @Test
    void testRightToLeft(){
        instance.setLocale(finland);
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertFalse(instance.isRightToLeft());

        instance.setLocale(us);
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertFalse(instance.isRightToLeft());

        instance.setLocale(arab);
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertTrue(instance.isRightToLeft());

        instance.setLocale(china);
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertFalse(instance.isRightToLeft());

        instance.setLocale(japan);
        System.out.println(instance.getLocale().getDisplayLanguage() + " is written right to left: " + instance.isRightToLeft());
        assertFalse(instance.isRightToLeft());
    }

    @Test
    void testNumberFormat(){
        instance.setLocale(finland);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12,34", instance.getFormattedDouble(12.34));

        instance.setLocale(us);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12.34", instance.getFormattedDouble(12.34));

        instance.setLocale(arab);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12.34", instance.getFormattedDouble(12.34));

        instance.setLocale(china);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12.34", instance.getFormattedDouble(12.34));

        instance.setLocale(japan);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedDouble(12.34));
        assertEquals("12.34", instance.getFormattedDouble(12.34));
    }

    @Test
    void testPercentageFormat(){
        instance.setLocale(finland);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50 %", instance.getFormattedPercent(0.5));


        instance.setLocale(us);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50%", instance.getFormattedPercent(0.5));

        instance.setLocale(arab);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50\u200E%\u200E", instance.getFormattedPercent(0.5));

        instance.setLocale(china);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50%", instance.getFormattedPercent(0.5));

        instance.setLocale(japan);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedPercent(0.5));
        assertEquals("50%", instance.getFormattedPercent(0.5));
    }

    @Test
    void testCurrencyFormat(){
        instance.setLocale(finland);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("12,34 €", instance.getFormattedCurrency(12.34));

        instance.setLocale(us);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("$12.34", instance.getFormattedCurrency(12.34));


        instance.setLocale(arab);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("\u200F12.34 د.إ.\u200F", instance.getFormattedCurrency(12.34));

        instance.setLocale(china);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("¥12.34", instance.getFormattedCurrency(12.34));

        instance.setLocale(japan);
        System.out.println("Decimal format for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedCurrency(12.34));
        assertEquals("￥12", instance.getFormattedCurrency(12.34));
    }

    @Test
    void testTemperatureFormat(){
        instance.setLocale(finland);
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("12,34°C", instance.getFormattedTemperature(12.34));

        instance.setLocale(us);
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("54.21°F", instance.getFormattedTemperature(12.34));

        instance.setLocale(arab);
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("م°12.34", instance.getFormattedTemperature(12.34));

        instance.setLocale(japan);
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("12.34°C", instance.getFormattedTemperature(12.34));

        instance.setLocale(china);
        System.out.println("12.34 celsius for: " + instance.getLocale().getDisplayLanguage());
        System.out.println(instance.getFormattedTemperature(12.34));
        assertEquals("12.34°C", instance.getFormattedTemperature(12.34));
    }
}