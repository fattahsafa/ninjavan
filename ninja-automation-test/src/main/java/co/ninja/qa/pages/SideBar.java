package co.ninja.qa.pages;

import co.ninja.qa.lib.utils.TestProperties;
import co.ninja.qa.lib.web.WebBrowser;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SideBar extends BasePage {
    private static final Logger logger = Logger.getLogger(SideBar.class);

    /* use svg vector instead of the button as the menu is collapsable */
    @FindBy(xpath = "//li[@path='/tracking']/*[local-name()='svg']")
    private WebElement trackingMenuItem;

    public SideBar(WebBrowser webBrowser) {
        super(webBrowser);
        PageFactory.initElements(webBrowser.webDriver, this);
    }

    /**
     * @functionality click on the Tracking menu item
     */
    public void openTrackingPage() {
        WebDriverWait wait;

        wait = new WebDriverWait(webBrowser.webDriver, Duration.ofSeconds(TestProperties.browserElementDetectTimeout));
        trackingMenuItem= wait.until(ExpectedConditions.elementToBeClickable(trackingMenuItem));
        /* step1: click on the Tracking menu item */
        logger.debug("Click on the Tracking menu item");

        /* click on image vector function works on some Windows version while clickImage is working on others and IOS */
        /* Ideally, we should check after the first click if the tracking page is opened or not.but performing another click will not hurt,
        and it has less performance effect than checking then clicking
         */
        try{
        webBrowser.click(trackingMenuItem);
        }
        catch (Exception exception)
        {
                webBrowser.clickImage(trackingMenuItem);
        }
    }
}
