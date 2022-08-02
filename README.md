 Automation Architecture

* The Web automation is a java maven project is built using Selenium over Java and long with TestNG framework and Page Object Model design pattern
* The API automation is a java maven project is built using RestAssured over Java and long with TestNG framework
* Each Test has a TestNG xml file with global and\or local data parameters to manage the input data outside the code
* There are also a general properties files (test.properties) that contains the following properties:
    * **report.directory**: report root directory
    * **report.central.directory**=central report directory that contains the reports from the latest run (for CI pipeline use)
    * **browser.type**: CHROME, FIREFOX OR EDGE
    * **browser.driver.path**: the path to the webdriver
    * **browser.page**.load.timeout: implicit page wait timeout
    * **browser.element.detect.timeout**: implicit timeout to wait for the element
    * **main.page.url**: website homepage
* The test results are summarized in an html report that is generated inside a timestampped directory in the **report.directory** path
* Two cloud VMs are created:
    * qa-automation-01: Centos machine that host Jenkins
    * qa-automation-02: Windows Server 2019 machine to run the tests remotely
* The tests can be run either using:
    * Windows command prompt: **mvn test -DsuiteXmlFile="path.to.testng.xml.file”**
    * Jenkins portal (username and password will be sent over email)
