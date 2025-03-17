package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class LocaleSingleton {
    private static LocaleSingleton instance = new LocaleSingleton();
    private Locale locale = Locale.ENGLISH;
    private ResourceBundle translations;
    private List<Locale> availableLocales = new ArrayList<>();

    private LocaleSingleton() {
        translations = ResourceBundle.getBundle("Translation", locale);

        Trace.out(Trace.Level.DEV, "Start finding available locales");
        /* Is this really how we are supposed to do it? Ugly mess for a simple job... */
        /* List all translation files */
        try {
            Enumeration<URL> en = LocaleSingleton.class.getClassLoader().getResources(".");
            while (en.hasMoreElements()) {
                URL url = en.nextElement();
                File file = new File(url.toURI());
                if (file.isDirectory())
                    Arrays.stream(file.listFiles())
                            .filter(f -> f.getName().startsWith("Translation_"))
                            .filter(f -> f.getName().endsWith(".properties"))
                            .forEach(f -> {
                                availableLocales.add(Locale.forLanguageTag(f.getName()
                                        .substring("Translation_".length(), f.getName().indexOf(".")).replace("_", "-")));
                            });
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

        //translations = ResourceBundle.getBundle("Translation", locale, );
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
}
