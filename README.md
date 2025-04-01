# OTP1
OTP1 Software Engineering Project 1





## Muu dokumentaatio


- [Palvelimen asennuksesta](docs/technical_md/install.md)
- [Docker kontit](docs/technical_md/containers.md)
- [JavaFX dockerissa](docs/technical_md/javafx_in_container.md)
- [Jenkins](docs/technical_md/jenkins_docker_maven_jdk_kubernetes.md)
- [MQTT broker Mosquitto](docs/technical_md/mosquitto.md)
- [GitHub Release](docs/technical_md/release_to_github.md)
- [Vanha pre-CI/CD API deployment](server/README.md)

Kaaviot
- [DB ERD](docs/diagrams/db.md)
- [Luokkakaavio UML](docs/technical_md/uml.md)
- [Luokkakaavio HttpQuery](docs/diagrams/uml_httpquery.md)
- [Luokkakaavio Manager](docs/diagrams/uml_manager.md)
- [Sekvenssikaavio](docs/diagrams/sequence.md)

Bruno API testauksen määrittelyt [bruno/](bruno/) hakemistossa, lokaali ja remote API:lle


<a name="top"></a>
[![I-SPY-U Application](./resources/banner_1.png)]()  
[![JDK21](https://img.shields.io/badge/JDK-21-512BD4)](https://docs.abblix.com/docs/technical-requirements)
[![language](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)]()
[![OS](https://img.shields.io/badge/OS-Linux%2C%20Windows-0078D4)]()
[![CPU](https://img.shields.io/badge/CPU-x86-FF8C00)]()
[![GitHub release](https://img.shields.io/github/v/release/0x6a4b/OTP1)](#)
[![GitHub release date](https://img.shields.io/github/release-date/0x6a4b/OTP1)](https://github.com/0x6a4b/OTP1/releases)
[![GitHub last commit](https://img.shields.io/github/last-commit/0x6a4b/OTP1)](#)
[![getting started](https://img.shields.io/badge/getting_started-guide-1D76DB)](./docs/getting-started-guide)
[![Free](https://img.shields.io/badge/free_for_non_commercial_use-brightgreen)](#-license)

⭐ Star us on GitHub — it motivates us a lot!


🔥 Why I-SPY-U is the best choice for sensor management — find out in our [presentation](./docs/presentation-eng.pdf) 📑


## !!! NOTICE !!!

This is an educational college project not meant for real deployment but for learning!!

## Table of Contents
- [About](#-about)
- [How to Build](#-how-to-deploy)
- [Documentation](#-documentation)
- [Feedback and Contributions](#-feedback-and-contributions)
- [License](#-license)
- [Contacts](#%EF%B8%8F-contacts)

## 🚀 About

**I-SPY-U** is a sensor management suite with Dockerized Spring Boot backend, MQTT Broker and JavaFX GUI client. Client is released as multiplatform JAR for Linux and Windows as well as VNC and X Display Docker containers. This ensures the following benefits:

- **IOT Standard communication with MQTT**: Efficiency and compatibility with IOT industry standard communications. 
- **Java and JavaFX client**: Multiplatform compatibility with a modern GUI possibilities. 
- **Dockerized**: Efficient, secure and fast deployment. Extra compatibility through Dockerized client. 
- **Multilanguage support i18n**: Supports multiple languages and character sets. Easy to add more languages with automatic loading of new translation files. 


New functionality added..



## 📝 How to Deploy

To build the packages, follow these steps:

```shell
# Ensure Docker or podmand is installed

# Install server
docker run -d -p 8080:8080 0x6a4b/otp:server

# Install MariaDB
docker run -d -p 3306:3306 mariadb

# Install MQTT Broker
docker run -d -p 1883:1883 eclipse/mosquitto

# Optionally you can install Dockerized clients
# For VNC access, running on server for example
docker run -d -p 5900:5900 0x6a4b/otp:client

# For X Display sharing, running locally containerized or on a server
docker run -d 0x6a4b/otp:clientX
```

## 📚 Documentation 

### Getting Started
Explore the [Getting Started Guide]().
In this guide, install/client usage is demonstrated.

To better understand the I-SPY-U product, we recommend visiting our [Documentation](./docs) site. There, you will find useful information about the product.



## 🤝 Feedback and Contributions

We've made every effort to implement all the main aspects of the OpenID protocol in the best possible way. However, the development journey doesn't end here, and your input is crucial for our continuous improvement.

> [!IMPORTANT]
> Whether you have feedback on features, have encountered any bugs, or have suggestions for enhancements, we're eager to hear from you. 

Please feel free to contribute by [submitting an issue](https://github.com/0x6a4b/OTP1/issues).



## 📃 License

This product is distributed under a proprietary license.

For non-commercial use, this product is available for free.

## 🗨️ Contacts

For more details about our products, services, or any general information regarding the I-SPY-U, feel free to reach out to us. We are here to provide support and answer any questions you may have. Below are the best ways to contact our team:

- **Email**: Send us your inquiries or support requests at [0x6a4b@0xa6ab.dev](mailto:0x6a4b@0xa6ab.dev).
- **Website**: Visit the official I-SPY-U page for more information: [I-SPY-U](https://otp1.censored.xxx).


We look forward to assisting you and ensuring your experience with our products is successful and enjoyable!

[Back to top](#top)
