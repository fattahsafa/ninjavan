package co.ninja.qa.pages;

import co.ninja.qa.lib.web.WebBrowser;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;


public class OrderPage extends BasePage{
    private static final Logger logger = Logger.getLogger(OrderPage.class);
    @FindBy(xpath = "//*[@testid='tracking-id']")
    private WebElement trackingIDLabel;

    @FindBy(xpath = "//button[.//span[text()='Print Airway Bill']]")
    private WebElement printAirwayBillButton;

    @FindBy(xpath = "//*[@data-key='x_bills_per_page']/*[text()='1 bills per page']")
    private WebElement oneBillPerPageButton;

    @FindBy(xpath = "//*[@data-key='x_bills_per_page']/*[text()='2 bills per page']")
    private WebElement twoBillsPerPageButton;

    @FindBy(xpath = "//*[@data-key='x_bills_per_page']/*[text()='3 bills per page']")
    private WebElement threeBillsPerPageButton;

    @FindBy(xpath = "//a[./span[@data-key='print']]")
    private WebElement printButton;
    public OrderPage(WebBrowser webBrowser) {
        super(webBrowser);
        PageFactory.initElements(webBrowser.webDriver, this);
    }

    /**
     *
     * @return
     * @functionality get the tracking id of the opened order page
     */
    public String getTrackingID()
    {
        String trackingID;
        logger.debug("Get the tracking ID");
        trackingID = trackingIDLabel.getText();
        logger.debug("Tracking ID= "+trackingID);

        return trackingID;
    }

    /**
     *
     * @functionality click on Print Airway Bill button
     */
    public String printAirwayBill(int numberOfBillsPerPage) throws Exception {
        WebDriverWait webDriverWait;
        String fileName;

        logger.debug("Click on Print Airway Bill button");
        webBrowser.click(printAirwayBillButton);
        webBrowser.switchToLastTab();

        webDriverWait = new WebDriverWait(webBrowser.webDriver, Duration.ofSeconds(20));
        switch (numberOfBillsPerPage){
            case 1:
                logger.debug("Click on 1 Bill per Page");
                webBrowser.click(oneBillPerPageButton);
                break;
            case 2:
                logger.debug("Click on 2 Bill per Page");
                webBrowser.click(twoBillsPerPageButton);
                break;
                case 3:
                logger.debug("Click on 3 Bill per Page");
                webBrowser.click(threeBillsPerPageButton);
                break;
            default:
                throw new Exception(numberOfBillsPerPage+ " is no supported");
        }
        logger.debug("Click on print button");
        printButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(printButton));
        fileName = printButton.getAttribute("href");
        fileName = fileName.substring(fileName.lastIndexOf("/")+1)+".pdf";
        logger.debug("File name= "+fileName);

        webBrowser.click(printButton);
        //webBrowser.switchToLastTab();

        logger.debug("Waiting for "+fileName+" to be downloaded");
        fileName = webBrowser.waitUntilFileDownloaded(fileName);

        return fileName;
    }

    /**
     *
     * @param billURI
     * @return
     * @throws IOException
     * @functioanlity read bill content
     */
    public String readPrintedBill(String billURI) throws IOException {
        String billContent;

        logger.debug("Read bill content from "+billURI);
        billContent = webBrowser.extractTextFromPDF(billURI);
        logger.debug("--------------------------Printed Bill content--------------------------"+"\n"+billContent+"\n"+"--------------------------End of Printed Bill content--------------------------");
return billContent;
    }
}
