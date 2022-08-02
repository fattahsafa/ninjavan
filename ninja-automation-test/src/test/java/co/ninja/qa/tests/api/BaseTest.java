package co.ninja.qa.tests.api;

import co.ninja.qa.api.NinjaAPI;
import co.ninja.qa.lib.utils.ReportUtils;
import co.ninja.qa.lib.utils.TestProperties;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class BaseTest {

    private static final Logger logger = Logger.getLogger(BaseTest.class);
    protected static ReportUtils reportUtils;
    protected NinjaAPI ninjaAPI;

    @BeforeSuite
    protected void suiteSetup() throws Exception {
        logger.info("Reading test properties");
        TestProperties.setProperties();
        logger.info("Initiate report");
        reportUtils = new ReportUtils(TestProperties.reportDirectory, TestProperties.centralReportDirectory);
    }

    @BeforeMethod
    protected void setup() throws Exception {
        logger.info("Initiate API accessed vi "+TestProperties.apiEndpoint);
        ninjaAPI = new NinjaAPI(TestProperties.apiEndpoint);
    }

    @AfterMethod
    protected void tearDown(ITestResult testResult) {
        logger.info("Setting test result");
        /* Attach results */
        if (testResult.getStatus() == ITestResult.FAILURE) {
            /* Test failed */
            Throwable exception;

            logger.info("Setting test result to FAIL");
            exception = testResult.getThrowable();
            reportUtils.addTestLog(Status.FAIL, exception.getLocalizedMessage());
            reportUtils.addTestLog(Status.FAIL, "Test Failed");

        } else if (testResult.getStatus() == ITestResult.SUCCESS) {
            /* Test Passed */
            logger.info("Setting test result to PASS");
            reportUtils.addTestLog(Status.PASS, "Test Passed");
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            /* Test Skipped */
            logger.info("Setting test result to SKIP");
            reportUtils.addTestLog(Status.SKIP, "Test Skipped");
        }
    }

    @AfterSuite
    protected void suiteTearDown() throws IOException {
        logger.info("Flushing the report");
        reportUtils.closeReport();
        reportUtils.markAsLatest();
    }
}
