# Sprint 5 review

## 1. User interface localization

**Identify text elements that require translation.**

We have gone through the UI and identified all elements that need translations and localizations.

Translations:
- buttons
- labels
- column headings

Localizations:
- temperature values
- dates and times

List of translations:

- user
English (US)
Login
Log Out
Username
Password
Sign Up
First Name
Last Name
Street
Postalcode
City
Country
Email
Remember Me

- devices
Devices
Add Device
Add a new device
Remove Device
Edit Device
Share Device
View Shares
View Device
Share Devices
Remove Share
My Devices
Open device
Back to device list

- table views
Name
Description
Model
Date
Key
Value
UUID
Number
Log entries
Log of last measurement
Measurement
Hourly
Daily
Weekly

Data
Config
Share
Sharing
Share role
Select role
Editor
Viewer
Set

Edit description
Device is shared to
Are you sure?
Cancel
Ok
You are removing a device!
This is destructive action!

- units
celsius = {0}°C
fahrenheit = {0}°F
temp.format = ###,###.##°F

User Interface has been fully translated and localized. Including reading direction, temperature and date formats.

Language selection implemented using the language's name in that specific language.

Our implementation dynamically reads all localization files, e.g. Translation_fi_FI.properties, at client startup and creates a list that view can access. It has a list of right to left writing locales as well as fahrenheit using locales. This way it dynamically works with any new locale saved in a properly formatted and named properties file. No other work needs to be implemented by a developer, only by the translator and copying of the new properties file.

### User Interface Localization
- Translated ui texts to each chosen language
- Created a language selection menu 
- Created a language singleton to manage selected language, date, date and time and temperature formats. Also have currency formats even if not implemented in current UI
- Changed ui layout for arabic, japanese, chinese, swedish, finnish and american english localizations


## 2. Database localization

Database doesn't really have such data that localization or more accurately translations would be needed. Database has been tested to fully function with Arabic even in login with username and password in Arabic.
Database uses a set temperature unit and unit is converted to local units in UI.
However we did add a translation table with key value pattern to be able to translate anything to any language flexibly as per the requirements.

Character encoding is UTF-8 and used for all locales and we do not save the locale of the user into the database at it seems preferable to get this from the system locale and user selection. This seems the most logical way in our case. If locale is set to US or other imperial temperature unit using locales we display fahrenheit.

### Database Localization
- Made sure database accepts UTF-8 characters
- Also tested to work with login/signup even as password
- Using a set unit for temperature and converting to fahrenheit in client side if need to. Automatically recognizes need if locale uses fahrenheit officially


## 3. Sprint and Product Backlog Update

### Product Backlog Update
- Wrote down users stories for each each localization step and estimated storypoints.


## 4. Identification of Localization Resources 

**Identify essential resources, such as translators and content management systems, for the localization process.**

First round of UI translations made with local llama which resulted in wrong translations => we had a translation that actually said 'shameful clown'.
We moved to use Google Translate in next step which seems to be a lot better.
Finally UI was translated with ChatGPT which made it very fast and is a great tool to do the first prototype level translations.

Localization formats were searched throughout web and had lot of conflicting information. The final translation and localization before release must go through professionals and competent services.

We would need to employ the services of professional, native, translator and localization services for our locales. We have Japanese, Chinese, Arabic(UAE), Swedish, US English and Finnish locales so all of these would need to be outsourced to a proficient company/localizer. We would also need to at least double check with another translator to make sure there are no mistakes made by the localization service.

We only need to translate and make sure all formats are correct as our service is just IoT sensor data, relatively small localization task compared to many other services.


### Localization Resources Identification
- Ai tools for initial translations
- Hiring natives to confirm/correct translations










# Sprint Planning and Review

