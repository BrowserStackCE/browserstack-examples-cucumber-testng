![Logo](https://www.browserstack.com/images/static/header-logo.jpg)

# BrowserStack Examples Cucumber TestNG <a href="https://cucumber.io"><img src="https://brandslogos.com/wp-content/uploads/images/large/cucumber-logo.png" alt="Cucumber" height="22" /></a> <a href="https://testng.org/"><img src="https://e7.pngegg.com/pngimages/640/776/png-clipart-testng-logo-software-testing-software-framework-computer-icons-automation-testing-angle-text.png" alt="TestNG" height="22" /></a>

## Introduction

TestNG is a testing framework designed to simplify a broad range of testing needs, from unit testing (testing a class in isolation of the others) to integration testing (testing entire systems made of several classes, several packages and even several external frameworks, such as application servers). Cucumber is a software tool that supports behavior-driven development (BDD).

This BrowserStack Example repository demonstrates a Selenium test framework written in Cucumber and TestNG with parallel testing capabilties. The Selenium test scripts are written for the open source [BrowserStack Demo web application](https://bstackdemo.com) ([Github](https://github.com/browserstack/browserstack-demo-app)). This BrowserStack Demo App is an e-commerce web application which showcases multiple real-world user scenarios, written in Next and React. The app is bundled with offers data, orders data and products data that contains everything you need to start using the app and run tests out-of-the-box.

The Selenium tests are run on different platforms like on-prem, docker and BrowserStack using various run configurations and test capabilities.



---

## Repository setup

- Clone the repository

- For this infrastructure configuration (i.e on-premise) ensure that the ChromeDriver executable is placed in the `/src/test/resources/` folder.

- Ensure you have the following dependencies installed on the machine
  - Java >= 8
  - Maven >= 3.1+
  - Gradle >= 5.0+

  Maven:
    ```sh
    mvn install
    ```

  Gradle:
    ```sh
    ./gradlew build
    ```

## About the tests in this repository

This repository contains the following Selenium tests:

| Module   | Test name                          | Description |
  | ---   | ---                                   | --- |
| E2E      | End to End Scenario                | This test scenario verifies successful product purchase lifecycle end-to-end and is also the default test executed in all the single test run profiles. |
| Login    | Login with given 'username' user         | This test verifies the login workflow with different types of valid login users. |
| Login    | Login as Locked User               | This test verifies the login workflow error for a locked user. |
| Offers   | Offers for Mumbai location     | This test mocks the GPS location for Mumbai and verifies that the product offers applicable for the Mumbai location are shown.   |
| Product  | Apply Apple Vendor Filter          | This test verifies that 9 Apple products are only shown if the Apple vendor filter option is applied. |
| Product  | Apply Lowest to Highest Order By   | This test verifies that the product prices are in ascending order when the product sort "Lowest to Highest" is applied. |
| User     | Login as User with no image loaded | This test verifies that the product images load for user: "image_not_loading_user" on the e-commerce application. Since the images do not load, the test case assertion fails.|
| User     | Login as User with existing Orders |  This test verifies that existing orders are shown for user: "existing_orders_user"  |
  
---


## Test infrastructure environments

- [On-premise/self-hosted](#OnPremise)
- [Docker](#docker)
- [BrowserStack](#browserstack)


## Configuring the maximum parallel test threads for this repository

For all the parallel run configuration profiles, you can configure the maximum parallel test threads by changing the settings below.

- Docker

  [docker-compose.yml](docker-compose.yml)
  
  NODE_MAX_INSTANCES = 5
  GRID_MAX_SESSION = 5
 

- BrowserStack

  - Maven:

    [pom.xml](pom.xml) 
    ```sh
    parallel-count = 5 
    ```
  
  - Command Line args:
    
     ```sh
    -Ddataproviderthreadcount= <Thread-count>
     ```

## Test Reporting

- [Allure reports](#generating-allure-reports)

---

# OnPremise

This infrastructure points to running the tests on your own machine using a browser (e.g. Chrome) using the browser's driver executables (e.g. ChromeDriver for Chrome). Selenium enables this functionality using WebDriver for many popular browsers.

## Prerequisites

- For this infrastructure configuration (i.e on-premise) ensure that the ChromeDriver executable is placed in the `/src/test/resources/` folder.

Note: The ChromeDriver version must match the Chrome browser version on your machine.

## Running Your Tests

### Run a specific test on your own machine

- How to run the test?

  To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
    ```sh
  mvn test -P on-prem
  ```

  Gradle:
    ```sh 
  ./gradlew on-prem
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn test -P on-prem -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  ./gradlew on-prem -Dtest-name="<Test scenario name>"
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login with given 'fav_user' user", "Login as Locked User", "Offers for Mumbai location"  or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.

- Output

  This run profile executes a specific test scenario on a single browser instance on your own machine.


### Run the entire test suite on your own machine

- How to run the test?

  To run the entire test suite on your own machine, use the following command:

  Maven:
  ```sh
  mvn test -P on-prem-suite
  ```

  Gradle:
  ```sh
  ./gradlew on-prem-suite
  ```

- Output

  This run profile executes the entire test suite sequentially on a single browser, on your own machine.

  
---

# Docker

[Docker](https://docs.docker.com/get-started/overview/) is an open source platform that provides the ability to package and test applications in an isolated environment called containers.

## Prerequisites

- Install and start [Docker](https://docs.docker.com/get-docker/).
- Note: Docker should be running on the test machine. Ensure Docker Compose is installed as well.
- Run `docker-compose pull` from the current directory of the repository.

## Running Your Tests

### Run a specific test on the docker infrastructure

- How to run the test?

  - Start the Docker by running the following command:

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
  ./gradlew docker
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:

  Maven:
  ```sh
  mvn test -P docker -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  ./gradlew docker -Dtest-name="<Test scenario name>"
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login with given 'fav_user' user", "Login as Locked User", "Offers for Mumbai location"  or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.


- After tests are complete, you can stop the Docker by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes a specific test scenario on a single browser deployed on a docker image.


### Run the entire test suite in parallel using Docker

- How to run the test?

  - Start the docker image first by running the following command:

  ```sh
  docker-compose up -d
  ```

  - To run the entire test suite in parallel on the docker image, use the following command:

  Maven:
  ```sh
  mvn test -P docker-parallel
  ```

  Gradle:
  ```sh
  ./gradlew docker-parallel
  ```

  - After the tests are complete stop the Selenium grid by running the following command:

  ```sh
  docker-compose down
  ```

- Output

  This run profile executes the entire test suite in parallel on a single browser, deployed on a docker image.

- Note: By default, this execution would run maximum 5 test threads in parallel on Docker. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

---

# BrowserStack

[BrowserStack](https://browserstack.com) provides instant access to 2,000+ real mobile devices and browsers on a highly reliable cloud infrastructure that effortlessly scales as testing needs grow.

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

  Alternatively, you can also hardcode username and access_key objects in the capabilities [.yml] file.

Note:
- We have configured a list of test capabilities in the [.yml] file. You can certainly update them based on your device / browser test requirements.
- The exact test capability values can be easily identified using the [Browserstack Capability Generator](https://browserstack.com/automate/capabilities)


## Running Your Tests

### Run a specific test on BrowserStack

In this section, we will run a single test on Chrome browser on Browserstack. To change test capabilities for this configuration, please refer to the `single` object in `capabilities-bstack-single.yml` file.

- How to run the test?

  - To run the default test scenario (e.g. End to End Scenario) on your own machine, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-single
  ```

  Gradle:
    ```sh
  ./gradlew bstack-single
  ```

  To run a specific test scenario, use the following command with the additional 'test-name' argument:
  Maven:
  ```sh
  mvn test -P bstack-single -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  ./gradlew bstack-single -Dtest-name="<Test scenario name>"
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g. "Login with given 'fav_user' user", "Login as Locked User", "Offers for Mumbai location" or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.


- Output

  This run profile executes a single test on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.


### Run the entire test suite in parallel on a single BrowserStack browser

In this section, we will run the tests in parallel on a single browser on Browserstack. Refer to `single` object in `capabilities-bstack-single.yml` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-parallel
  ```
  Gradle:
    ```sh
  ./gradlew bstack-parallel
  ```


- Output

  This run profile executes the entire test suite in parallel on a single BrowserStack browser. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

  - Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.


### Run the entire test suite in parallel on multiple BrowserStack browsers

In this section, we will run the tests in parallel on multiple browsers on Browserstack. Refer to the `parallel` object in `capabilities-bstack-parallel-browsers.yml` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on multiple BrowserStack browsers, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-parallel-browsers
  ```

  Gradle:
  ```sh
  ./gradlew bstack-parallel-browsers
  ```

### [Web application hosted on internal environment] Running your tests on BrowserStack using BrowserStackLocal

#### Prerequisites

- Clone the [BrowserStack demo application](https://github.com/browserstack/browserstack-demo-app) repository.
  ```sh
  git clone https://github.com/browserstack/browserstack-demo-app
  ``` 
- Please follow the README.md on the BrowserStack demo application repository to install and start the dev server on localhost.
- In this section, we will run a single test case to test the BrowserStack Demo app hosted on your local machine i.e. localhost. Refer to the `single_local` object in `capabilities-bstack-local-single.yml` file to change test capabilities for this configuration.
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
  ./gradlew bstack-local
  ```

  To run a specific test scenario, use the following command with the additional test-name argument:
  Maven:
  ```sh
  mvn test -P bstack-local -Dtest-name="<Test scenario name>"
  ```

  Gradle:
  ```sh
  ./gradlew bstack-local -Dtest-name="<Test scenario name>"
  ```

  where,  the argument 'test-name' can be any Cucumber scenario name configured in this repository.

  E.g.  "Login with given 'fav_user' user", "Login as Locked User", "Offers for Mumbai location" or any of the other test scenario names, as outlined in [About the tests in this repository](#About-the-tests-in-this-repository) section.


- Output

  This run profile executes a single test on an internally hosted web application on a single browser on BrowserStack. Please refer to your BrowserStack dashboard(https://automate.browserstack.com/) for test results.


### [Web application hosted on internal environment] Run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test the internally hosted website in parallel on a single browser on Browserstack. Refer to the `single_local` object in `capabilities-bstack-local-single.yml` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:
  Maven:
  ```sh
  mvn test -P bstack-local-parallel
  ```

  Gradle:
  ```sh
  ./gradlew bstack-local-parallel
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on a single browser on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

- Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

### [Web application hosted on internal environment] Run the entire test suite in parallel on multiple BrowserStack browser using BrowserStackLocal

In this section, we will run the test cases to test the internally hosted website in parallel on multiple browsers on Browserstack. Refer to the `parallel_local` object in `capabilities-local-parallel-browsers.yml` file to change test capabilities for this configuration.

- How to run the test?

  To run the entire test suite in parallel on a single BrowserStack browser using BrowserStackLocal, use the following command:

  Maven:
  ```sh
  mvn test -P bstack-local-parallel-browsers
  ```

  Gradle:
    ```sh
  ./gradlew bstack-local-parallel-browsers
  ```

- Output

  This run profile executes the entire test suite on an internally hosted web application on multiple browsers on BrowserStack. Please refer to your [BrowserStack dashboard](https://automate.browserstack.com/) for test results.

- Note: By default, this execution would run maximum 5 test threads in parallel on BrowserStack. Refer to the section ["Configuring the maximum parallel test threads for this repository"](#Configuring-the-maximum-parallel-test-threads-for-this-repository) for updating the parallel thread count based on your requirements.

### Using cucumber tags
tags are used to associate a test like smoke, regression etc. with a particular scenario.

Tag fulfils the following purposes:
 -  If we have many scenarios in the feature file, to keep them in one group, we use tags in Cucumber, through which we will be able to prepare reports for specific scenarios under the same tag.
-  By default, Cucumber executes all the scenarios inside the feature file, but if we need to execute or skip any specific scenario under a specific test, so we can declare scenarios within a tag.

Command : 
  Maven:
```sh
  mvn test -Dcucumber.filter.tags="@tagname"
  ```
  Gradle:
```sh
  ./gradlew test -Dcucumber.filter.tags="@tagname"
  ```
For this repository you can use following tags > @regression, @e2e, @loginValid, @loginInvalid, @offers, @vendorFilter, @userfeature > This is will run scenarios with this tags on browserstack 

## Generating Allure Reports

  In this section, we will generate and serve allure reports for maven test runs.

 Maven:
- Generate Report using the following command: `mvn allure:report`
- Serve the Allure report on a server: `mvn allure:serve`

 Gradle:
- Generate Report using the following command: `./gradlew allurereport`
- Serve the Allure report on a server: `./gradlew allureserve`


## Additional Resources

- View your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
- Documentation for writing [Automate test scripts in Java](https://www.browserstack.com/automate/java)
- Customizing your tests capabilities on BrowserStack using our [test capability generator](https://www.browserstack.com/automate/capabilities)
- [List of Browsers & mobile devices](https://www.browserstack.com/list-of-browsers-and-platforms?product=automate) for automation testing on BrowserStack
- [Using Automate REST API](https://www.browserstack.com/automate/rest-api) to access information about your tests via the command-line interface
- Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)
- For testing public web applications behind IP restriction, [Inbound IP Whitelisting](https://www.browserstack.com/local-testing/inbound-ip-whitelisting) can be enabled with the [BrowserStack Enterprise](https://www.browserstack.com/enterprise) offering

