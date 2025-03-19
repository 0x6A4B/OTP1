# Localization API

i18n, l10n

## Resource bundle handling

Class will automatically load every translation property file saved in correct format, e.g.
>Translation_fi_FI.properties


## Language name localized

```
instance.getTranslation("language");
```
![img](img/localised_language_display.png)

## Date format

Long date time format
![img](img/longdatetimeformat.webp)

```
instance.getLongFormattedDateTime(date);
```

Short date time format
![img](img/shortdatetimeformat.webp)

```
instance.getShortFormattedDateTime(date);
```

---
Long date format
![img](img/localised_date_longformat.png)

```
instance.getLongFormattedDate(date);
```

Short date format
![img](img/localised_date_shortformat.png)

```
instance.getShortFormattedDate(date);
```

## Translation

Get translation. Case insensitive, converts spaces to underscores (_)
> Edit Device  
gets converted to  
edit_device

```
instance.getTranslation("login")
```

## Getting and setting locale

Get available locales as List<Locale>
```
instance.getAvailableLocales();
```

Set selected locale
```
instance.setLocale(locale);
```

## Reading direction

Get locale's reading direction, is it right to left

```
instance.isRightToLeft()
```
Return value
>true / false