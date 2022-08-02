package co.ninja.qa.tests.web;

import co.ninja.qa.lib.utils.TestProperties;
import co.ninja.qa.pages.OrderPage;
import co.ninja.qa.pages.TrackingPage;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PrintAirwayBillTests extends BaseTest {
    private static final Logger logger = Logger.getLogger(PrintAirwayBillTests.class);
    private TrackingPage trackingPage;
    private OrderPage orderPage;

    @BeforeMethod
    protected void setup() throws Exception {
        super.setup();
        trackingPage = new TrackingPage(webBrowser);
        orderPage = new OrderPage(webBrowser);
    }

    @Parameters({"email", "password"})
    @Test(suiteName = "Functional", groups = "Positive Flows", enabled = true)
    private void testPrintAirwayBill(@Optional String email, @Optional String password) throws Exception {
        String trackingID;
        String orderTrackingID;
        int stepNumber = 0;
        String step;
        String billFileName;
        String printedBillContent;

        logger.info("Test: Print Airway Bill");
        reportUtils.addTest("Test: Print Airway Bill");
        reportUtils.addTestLog(Status.INFO, "Browser: " + TestProperties.browserType);

        /* step 1: open the login page */
        stepNumber++;
        step = "step"+stepNumber+": login to the user account";
        /* step 2: login using the username and password */
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        login(email, password);

        /* step 3: close popup */
        stepNumber++;
        step = "step"+stepNumber+": close popup";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        mainPage.closePopup();

        /* step 4: open the tracking page */
        stepNumber++;
        step = "step"+stepNumber+": open the tracking page";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        sideBar.openTrackingPage();

        /* step 5: click on the search button */
        stepNumber++;
        step = "step"+stepNumber+": click on the Search button";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        trackingPage.search("");

        /* step 6: click on the first row tracking id */
        stepNumber++;
        step = "step"+stepNumber+": click on the first row tracking id";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        trackingID = trackingPage.getOrderTrackingID(1);
        trackingPage.clickOrderTrackingID(1);
        reportUtils.addTestLog(Status.INFO, "Selected trackind ID is "+trackingID);
        logger.info("Selected trackind ID is "+trackingID);

        /* step7: validate the Tracking ID of the opened Order Tab */
        stepNumber++;
        step = "step"+stepNumber+": validate the Tracking ID of the opened Order Tab";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        orderTrackingID = orderPage.getTrackingID();
        reportUtils.addTestLog(Status.INFO, "The tracking ID in the Order Details page is "+orderTrackingID);
        logger.info("The tracking ID in the Order Details page is "+orderTrackingID);
        Assert.assertEquals(trackingID, orderTrackingID, "The tracking ID in the Order Page doesn't match the one in the tracking page");

        /* step8: print the airway bill */
        stepNumber++;
        step = "step"+stepNumber+": print the Airway Bill as one bill per page";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        billFileName = orderPage.printAirwayBill(1);

        /* step9: check if the generated Airway Bill belongs to the selected order */
        stepNumber++;
        step = "step"+stepNumber+": check if the generated Airway Bill belongs to the selected order";
        logger.info(step);
        reportUtils.addTestLog(Status.INFO, step);
        printedBillContent = orderPage.readPrintedBill(billFileName);
        Assert.assertTrue(printedBillContent.contains(orderTrackingID), "The printed bill doesn't belong to the selected order");
    }
}
