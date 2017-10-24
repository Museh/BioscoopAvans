# Avans Bioscoop
Avans Bioscoop is a Cinema application written in Spring Java, following the Spring MVC principles.

# What does it do?
- Show an overview of movies in viewings that are about to start
- Possibility to search for a specific movie
- Show the details of a movie such as: ratings, a description and much more
- Allow the purchase of a ticket with different rates and specials (like adding popcorn to your ticket)
- Automatic seat assignment
- Allowing the purchaser to print their ticket

# Requirements
- Git for clone
- JDK version 1.8 or higher
- Maven, you still need to bind maven with your system in your System environmental variables (searching for the term `environmental` or `omgevingsvariabelen` will show the option in your search menu)

# Installation with IntelliJ
1. Clone this project to your location of choice with:
```sh
$ git clone https://github.com/Profijt/bioscoop.git
```
2. Open IntelliJ and select `import project`
3. Search for the cloned project and select the `pom.xml` and click `ok`
4. Wait for IntelliJ to finish building
2. Start the application, simply by running it with the `green play button`

# Installation with Maven, without IDE
1. Open your commandline
2. Change direction to a location of choice to store the project
3. Clone the project with the following command:
```sh
$ git clone https://github.com/Profijt/bioscoop.git
```
4. Change the directory to the root of the project
5. Start the project with 
```sh
$ mvn spring-boot:run
```

