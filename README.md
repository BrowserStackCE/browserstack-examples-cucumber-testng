# Browserstack Examples Cucumber TestNG <a href="https://cucumber.io"><img src="https://brandslogos.com/wp-content/uploads/images/large/cucumber-logo.png" alt="Cucumber" height="22" /></a> <a href="https://junit.org/junit5/"><img src="https://junit.org/junit5/assets/img/junit5-logo.png" alt="JUnit5" height="22" /></a> <a href="https://browserstack.com"><img src="https://camo.githubusercontent.com/799a5c97a4d00394703cf20a5de308784c5454c05726b4c6ba559397644e58d2/68747470733a2f2f643938623874316e6e756c6b352e636c6f756466726f6e742e6e65742f70726f64756374696f6e2f696d616765732f6c61796f75742f6c6f676f2d6865616465722e706e673f31343639303034373830" alt="Browserstack" height="22" /></a>

## Introduction

TestNG is a testing framework designed to simplify a broad range of testing needs, from unit testing (testing a class in isolation of the others) to integration testing (testing entire systems made of several classes, several packages and even several external frameworks, such as application servers). Cucumber is a software tool that supports behavior-driven development (BDD).

This BrowserStack Example repository demonstrates a Selenium test framework written in Cucumber and TestNG  with parallel testing capabilties. The Selenium test scripts are written for the open source [BrowserStack Demo web application](https://bstackdemo.com) ([Github](https://github.com/browserstack/browserstack-demo-app)). This BrowserStack Demo App is an e-commerce web application which showcases multiple real-world user scenarios, written in Next and React. The app is bundled with offers data, orders data and products data that contains everything you need to start using the app and run tests out-of-the-box.

The Selenium tests are run on different platforms like on-prem, docker and BrowserStack using various run configurations and test capabilities.

---

## Repository setup

- Clone the repository

  `git clone https://github.com/browserstack/browserstack-examples-cucumber-testng.git`

- Install the following dependencies
  - Java >= 8
  - Maven >= 3.1+
  - Gradle >= 5.0+

Note: To change Java version update `maven.compiler.source` and `maven.compiler.target` in `pom.xml` or `sourceCompatibility` and `targetCompatibility` in `build.gradle.kts`

## Test infrastructure environments

- [On-premise/self-hosted](#on-premise-self-hosted)
- [Docker](#docker)
- [BrowserStack](#browserstack)

## Test Reporting

- [Allure reports](#generating-allure-reports)

---

# On Premise / Self Hosted

This infrastructure points to running the Selenium tests on your own machine using a browser (e.g. Chrome) using the browser's driver executables (e.g. ChromeDriver for Chrome). Selenium enables this functionality using WebDriver for many popular browsers.

## Prerequisites

- For this infrastructure configuration (i.e on-premise), ensure that the ChromeDriver executable is placed in the `/src/test/resources/drivers` folder.

Note: The ChromeDriver version must match the Chrome browser version on your machine.

## Running Your Tests

### Run a specific test on your own machine

- How to run the test?

  To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
    ```sh
  mvn install -P on-prem
  ```

  Gradle:
    ```sh 
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn install -P -Dcucumber.options="<Feature File Location>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "End to End Scenario", "Login as username", "Login as Locked User", "Offers for mumbai geo-location", "Apply Apple Vendor Filter", "Apply Lowest to Highest Order By", "Login as User with no image loaded", "Login as User with existing Orders"

- Output

  This run profile executes a specific test scenario on a single browser instance on your own machine.


### Run the entire test suite on your own machine

- How to run the test?

  To run the entire test suite on your own machine, use the following command:

  Maven:
  ```sh
  mvn install -P on-prem-suite
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

- Output

  This run profile executes the entire test suite sequentially on a single browser, on your own machine.

  
---

# Docker

Docker is an open platform for developing, shipping, and running applications. Docker enables you to separate your applications from your infrastructure so you can deliver software quickly. By taking advantage of Docker�s methodologies for shipping, testing, and deploying code quickly, you can significantly reduce the delay between writing code and running it in production.

## Prerequisites

- Install and start [Docker](https://docs.docker.com/get-docker/).
- Note: Docker should be running on the test machine. Ensure Docker Compose is installed as well.
- Run `docker-compose pull` from the current directory of the repository.

## Running Your Tests

### Run a specific test on the docker infrastructure

- How to run the test?

  - Start the Selenium Grid hosted within the Docker by running the following command:

  ```sh
  docker-compose up -d
  ```

  - To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
  ```sh
  mvn test -P docker
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn install -P docker -Dcucumber.options="<Feature File Location>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "End to End Scenario", "Login as username", "Login as Locked User", "Offers for mumbai geo-location", "Apply Apple Vendor Filter", "Apply Lowest to Highest Order By", "Login as User with no image loaded", "Login as User with existing Orders"


- After tests are complete, you can stop the Selenium Grid by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes a specific test scenario on a single browser deployed on a Selenium Grid docker image.


### Run the entire test suite in parallel using Docker

- How to run the test?

  - Start the Selenium Grid docker image first by running the following command:

  ```sh
  docker-compose up -d
  docker-compose scale chromenode=3
  ```

  - To run the entire test suite in parallel on the docker image, use the following command:

  Maven:
  ```sh
  mvn test -P docker-parallel
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  - After the tests are complete stop the Selenium grid by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes the entire test suite in parallel on a single browser, deployed on a Selenium Grid docker image


---

# BrowserStack

BrowserStack Automate provides instant Selenium testing on desktop browsers, real iOS and Android devices.

## Prerequisites

- Create a new [BrowserStack account](https://www.browserstack.com/users/sign_up) or use an existing one.
- Identify your BrowserStack username and access key from the [BrowserStack Automate Dashboard](https://automate.browserstack.com/) and export them as environment variables using the below commands.

  - For \*nix based and Mac machines:

  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

  - For Windows:

  ```shell
  set BROWSERSTACK_USERNAME=<browserstack-username>
  set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```

Note:
- We have configured a list of test capabilties in the [caps.json](resources/conf/caps/caps.json) file. You can certainly update them based on your device / browser test requirements.
- The exact test capability values can be easily identified using the [Browserstack Capability Generator](https://browserstack.com/automate/capabilities)


## Running Your Tests

### Run a specific test on BrowserStack

In this section, we will run a single the test on Chrome browser on Browserstack. To change test capabilities for this configuration, please refer to `single` object in `caps.json` file.

- How to run the test?

  - To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-single
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:
  Maven:
  ```sh
  mvn install -P bstack-single -Dcucumber.options="<Feature File Location>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "End to End Scenario", "Login as username", "Login as Locked User", "Offers for mumbai geo-location", "Apply Apple Vendor Filter", "Apply Lowest to Highest Order By", "Login as User with no image loaded", "Login as User with existing Orders"


- Output

  This run profile executes a single test on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.


### Run the entire test suite in parallel on a single BrowserStack browser

In this section, we will run the tests in parallel on a single browser on Browserstack. Refer to `single` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser, use the following command:

  Maven:
  ```sh
  mvn install -P bstack-parallel
  ```
  Gradle:
    ```sh
  <Gradle command>
  ```


- Output

  This run profile executes the entire test suite in parallel on a single BrowserStack browser. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.


### Run the entire test suite in parallel on multiple BrowserStack browsers

In this section, we will run the tests in parallel on multiple browsers on Browserstack. Refer to `parallel` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on multiple BrowserStack browsers, use the following command:

  Maven:
  ```sh
  mvn compile exec:java -P bstack-parallel-browsers
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

### [Web application hosted on internal environment] Running your tests on BrowserStack using BrowserStackLocal

#### Prerequisites

- Clone the [BrowserStack demo application](https://github.com/browserstack/browserstack-demo-app) repository.
  ```sh
  git clone https://github.com/browserstack/browserstack-demo-app
  ``` 
- Please follow the README.md on the BrowserStack demo application repository to install and start the dev server on localhost.
- In this section, we will run a single test case to test the BrowserStack Demo app hosted on your local machine i.e. localhost. Refer to `single_local` object in `caps.json` file to change test capabilities for this configuration.
- Note: You may need to provide additional BrowserStackLocal arguments to successfully connect your localhost environment with BrowserStack infrastructure. (e.g if you are behind firewalls, proxy or VPN).
- Further details for successfully creating a BrowserStackLocal connection can be found here:

  - [Local Testing with Automate](https://www.browserstack.com/local-testing/automate)
  - [BrowserStackLocal Java GitHub](https://github.com/browserstack/browserstack-local-java)


### [Web application hosted on internal environment] Run a specific test on BrowserStack using BrowserStackLocal

- How to run the test?

  - To run the default test scenario (e.g. End to End Scenario) on a single BrowserStack browser using BrowserStackLocal, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-local
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

  To run a specific test scenario, use the following command with the additional test-name argument:
  Maven:
  ```sh
  mvn install -P bstack-local -Dcucumber.options="<Feature File Location>"
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "End to End Scenario", "Login as username", "Login as Locked User", "Offers for mumbai geo-location", "Apply Apple Vendor Filter", "Apply Lowest to Highest Order By", "Login as User with no image loaded", "Login as User with existing Orders"


- Output

  This run profile executes a single test on an internally hosted web application on a single browser on BrowserStack. Please refer to your BrowserStack dashboard(https://automate.browserstack.com/) for test results.


### [Web application hosted on internal environment] Run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test internally hosted website in parallel on single browser on Browserstack. Refer to `single_local` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:
  Maven:
  ```sh
  mvn install -P bstack-local-parallel
  ```

  Gradle:
  ```sh
  <Gradle command>
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

### [Web application hosted on internal environment] Run the entire test suite in parallel on multiple BrowserStack browser using BrowserStackLocal

In this section, we will run the testcases to test internally hosted website in parallel on multiple browsers on Browserstack. Refer to `parallel_local` object in `caps.json` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:

  Maven:
  ```sh
  mvn compile exec:java -P bstack-local-parallel-browsers
  ```

  Gradle:
    ```sh
  <Gradle command>
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on multiple browsers on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

## Generating Allure Reports

- Serve the Allure report on a server: `allure serve allure-results` 

## Addtional Resources

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customizing your tests capabilities on BrowserStack using our [test capabilty generator](https://www.browserstack.com/automate/capabilities)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for Selenium testing on BrowserStack
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Entrprise](https://www.browserstack.com/enterprise) offering