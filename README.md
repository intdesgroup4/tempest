# tempest

A weather app targeted at farmers, for Interaction Design coursework


# Setup

## Project Structure

`src/` contains the bulk of the Java source code.
`lib/` contains jar libraries that the source code is built with and requires to function.
`resources/` contain resources that will be packed into the built JAR itself (e.g. images)

Your build environment of choice should be setup to take this into account.


## Agro API Key

A file should be created at `resources/agro-api-key` with a single line containing the API key. This has been carefully placed in the .gitignore; please do not distribute it, as it could be very expensive!


# Useful stuff

The JDK I am using:

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

The page which helped me set up JavaFX in IntelliJ:

https://www.jetbrains.com/help/idea/preparing-for-javafx-application-development.html

JavaFX scene builder is also good stuff. You can edit the scenes (.fxml files in src/scenes) with it on a drag and drop way (**be sure to download version 2.0; 1.1 is buggy!**)

http://www.oracle.com/technetwork/java/javafxscenebuilder-1x-archive-2199384.html
