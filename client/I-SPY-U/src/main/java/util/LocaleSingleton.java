package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class LocaleSingleton {
    private static LocaleSingleton instance = new LocaleSingleton();
    private Locale locale = Locale.US;
    private ResourceBundle translations;
    private List<Locale> availableLocales = new ArrayList<>();

    private LocaleSingleton() {
        translations = ResourceBundle.getBundle("Translation", locale);

        Trace.out(Trace.Level.DEV, "Start finding available locales");
        /* Is this really how we are supposed to do it? Ugly mess for a simple job... */
        /* List all translation files */
        try {
            Enumeration<URL> en = LocaleSingleton.class.getClassLoader().getResources("");

            // In JAR
            // TODO: FIX this ugly hack => make sure works in development as well as in deployed JAR file
            if (!en.hasMoreElements()){
                CodeSource src = LocaleSingleton.class.getProtectionDomain().getCodeSource();
                if (src != null) {
                    URL jar = src.getLocation();
                    ZipInputStream zip = new ZipInputStream(jar.openStream());
                    while(zip.available() > 0){
                        ZipEntry entry = zip.getNextEntry();
                        if (entry == null) break;
                        if (entry.getName().endsWith(".properties") || entry.getName().startsWith("Translation_")) {
                            Trace.out(Trace.Level.DEV, "FOUND: " + entry.getName());
                            availableLocales.add(Locale.forLanguageTag(entry.getName()
                                    .substring("Translation_".length(), entry.getName().indexOf(".")).replace("_", "-")
                            ));
                        }
                    }
                }
            }



            while (en.hasMoreElements()) {
                URL url = en.nextElement();

                File file = new File(url.toURI());
                Trace.out(Trace.Level.DEV, "X: " + url + " - " + file);
                if (file.isDirectory())
                    Arrays.stream(file.listFiles())
                            .filter(f -> f.getName().startsWith("Translation_"))
                            .filter(f -> f.getName().endsWith(".properties"))
                            .forEach(f ->
                                availableLocales.add(Locale.forLanguageTag(f.getName()
                                        .substring("Translation_".length(), f.getName().indexOf(".")).replace("_", "-"))));
            }
        } catch (Exception e) {
            Trace.out(Trace.Level.ERR, "Unable to find available locales: " + e.getMessage());
        }
        availableLocales.forEach(l -> Trace.out(Trace.Level.DEV, "Available locale: " + l));
        Trace.out(Trace.Level.DEV, "End finding available locales");
    }

    public static LocaleSingleton getInstance() { return instance; }

    public Locale getLocale() { return locale; }
    public void setLocale(Locale locale) {
        this.locale = locale;

        try {
            String bundleFileName = "Translation_" + locale.getLanguage() + "_" + locale.getCountry() + ".properties";
            InputStream stream = LocaleSingleton.class.getClassLoader().getResourceAsStream(bundleFileName);
            translations = new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Locale> getAvailableLocales() {
        availableLocales.forEach(l -> Trace.out(Trace.Level.DEV, l.getDisplayLanguage() + "(" + l.getDisplayCountry() + ")"));
        return availableLocales;
    }

    public String getTranslation(String key) {
        key = key.toLowerCase().replace(" ", "_");
        if (translations.containsKey(key))
            return translations.getString(key);

        /* TODO: use english as fallback */
        /* If that fails then return the key? */
        return key;
    }

    // TODO: FIX dutty hack
    public String getLanguageName(Locale locale) {
        Locale chosen = this.locale;
        setLocale(locale);
        String language = getTranslation("language");
        setLocale(chosen);
        return language;
    }

    public String getLongFormattedDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            ((SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, locale))
                .toLocalizedPattern()
                , locale);

        return dateFormat.format(date);
    }

    public String getShortFormattedDateTime(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                ((SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale))
                        .toLocalizedPattern()
                , locale);

        return dateFormat.format(date);
    }

    public String getShortFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, locale))
                        .toLocalizedPattern()
                , locale);
        return dateFormat.format(date);
    }

    public String getLongFormattedDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                ((SimpleDateFormat) DateFormat.getDateInstance(DateFormat.LONG, locale))
                        .toLocalizedPattern()
                , locale);

        return dateFormat.format(date);
    }

    public boolean isRightToLeft() {
        // https://www.loc.gov/standards/iso639-2/php/code_list.php
        // https://lingohub.com/blog/right-to-left-vs-left-to-right
        List<String> list = Arrays.asList(
                "ar", "he", "fa", "ur", "ug", "ks", "ps", "ku", "pa", "sd"
        );
        return list.contains(locale.getLanguage());
    }

    public String getFormattedDouble(double number) {
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        return format.format(number);
    }

    public String getFormattedPercent(double number) {
        NumberFormat format = NumberFormat.getPercentInstance(locale);
        return format.format(number);
    }

    public String getFormattedCurrency(double number) {
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(number);
    }

    // use celsius as default? or kelvin as it's the SI unit?
    // https://numberformat.app/
    // https://industryarabic.com/numbers-in-arabic/
    public String getFormattedTemperature(double temperature) {
        List<String> fahrenheitList = Arrays.asList("US", "BS", "BZ", "KY", "PW");
        boolean celsius = !fahrenheitList.contains(locale.getCountry());
        String tempSymbol = celsius ? getTranslation("celsius") : getTranslation("fahrenheit");
        double convertedTemperature = celsius ? temperature : temperature * 1.8 + 32;
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        format.setMaximumFractionDigits(2);
        return MessageFormat.format(getTranslation(tempSymbol).toUpperCase(), format.format(convertedTemperature), locale);
    }

}
