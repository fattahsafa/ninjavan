package co.ninja.qa.tests.web;

import co.ninja.qa.lib.utils.ReportUtils;
import co.ninja.qa.lib.utils.TestProperties;
import co.ninja.qa.lib.web.WebBrowser;
import co.ninja.qa.pages.LoginPage;
import co.ninja.qa.pages.MainPage;
import co.ninja.qa.pages.SideBar;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Base64;

public class BaseTest {
    private static final Logger logger = Logger.getLogger(BaseTest.class);
    protected static ReportUtils reportUtils;
    protected WebBrowser webBrowser;
    protected LoginPage loginPage;
    protected MainPage mainPage;
    protected SideBar sideBar;

    @BeforeSuite
    protected void suiteSetup() throws Exception {
        logger.info("Reading test properties");
        TestProperties.setProperties();
        logger.info("Initiate report");
        reportUtils = new ReportUtils(TestProperties.reportDirectory, TestProperties.centralReportDirectory);
    }

    @BeforeMethod
    protected void setup() throws Exception {
        logger.info("Initiate WebBrowser of type " + TestProperties.browserType);
        webBrowser =
                new WebBrowser(
                        TestProperties.browserType,
                        TestProperties.browserElementDetectTimeout,
                        TestProperties.browserPageLoadTimeout, TestProperties.browserDownloadDirectory, TestProperties.browserDownloadTimeout);

        webBrowser.openPage(TestProperties.mainPageURL);
        loginPage = new LoginPage(webBrowser);
        mainPage = new MainPage(webBrowser);
        sideBar = new SideBar(webBrowser);
    }

    @Parameters({"email", "password", "customerName"})
    protected void login(@Optional String email, @Optional String password) {
        /* login in */
        String loginEmail;
        String loginPassword;

        if (email != null) {
            /* use email and password from testng */
            logger.info("Login using credentials from TestNG xml file");
            loginEmail = email;
            loginPassword = password;
        } else {
            /* use email and password from .properties file */
            logger.info("Login using credentials from .properties file");
            loginEmail = TestProperties.loginEmail;
            loginPassword = TestProperties.loginPassword;
        }
        logger.info(String.format("Login with email <%s> and password <%s>", loginEmail, Base64.getEncoder().encodeToString(loginPassword.getBytes())));
        loginPage.signIn(loginEmail, loginPassword);
    }

    @AfterMethod
    protected void tearDown(ITestResult testResult) {
        String screenshotFilePath;

        logger.info("Setting test result");
        /* Attach results */
        if (testResult.getStatus() == ITestResult.FAILURE) {
            /* Test failed */
            Throwable exception;

            logger.info("Setting test result to FAIL");
            exception = testResult.getThrowable();
            reportUtils.addTestLog(Status.FAIL, exception.getLocalizedMessage());
            reportUtils.addTestLog(Status.FAIL, "Test Failed");

            screenshotFilePath =
                    ReportUtils.captureScreenshot(webBrowser, TestProperties.reportDirectory);
            reportUtils.attachScreenshot(screenshotFilePath);

        } else if (testResult.getStatus() == ITestResult.SUCCESS) {
            /* Test Passed */
            logger.info("Setting test result to PASS");
            reportUtils.addTestLog(Status.PASS, "Test Passed");
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            /* Test Skipped */
            logger.info("Setting test result to SKIP");
            reportUtils.addTestLog(Status.SKIP, "Test Skipped");
        }

        /* Close the browser */
        try {
            logger.info("Closing the web browser");
            webBrowser.quit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @AfterSuite
    protected void suiteTearDown() throws IOException {
        logger.info("Flushing the report");
        reportUtils.closeReport();
        reportUtils.markAsLatest();
    }
}
