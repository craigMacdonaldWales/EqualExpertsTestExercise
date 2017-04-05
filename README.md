# EqualExpertsTestExercise


JAVA VERSION: 1.8

Dependencies:
MAVEN
JAVA JRE (developed in version 1.8, backwards compatibilities not tested)
Chrome driver
Firefox driver

Driver locations:
The CoreFunctions.driverHandle method uses System.getProperty to check for any existing instances of the webdriver.chrome.driver and webdriver.gecko.driver
system properties. If found, these values will be used to locate the relevant driver executables for browser launch.

If not found, the expected locations for the driver executables are as follows:

MAC OS X
//chromedriver//chromedriver
//geckodriver//geckodriver

Windows
C:\\chromedriver_win32\\chromedriver.exe
C:\\GeckoDriver\\geckodriver.exe

To run:
1. download and unzip the project.
2. in a command prompt or terminal, CD to the project folder where the POM file resides.
3a. to run against Chrome, execute the following command:

  mvn clean install "-Dbrowsertype=Chrome"
  or
  mvn clean install
  (chrome is the default option if no browser is specified

3b. to run against Firefox, execute the following command:
  mvn clean install "-Dbrowsertype=Firefox"

