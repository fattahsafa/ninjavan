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

public class MainPage extends BasePage {

    private static final Logger logger = Logger.getLogger(MainPage.class);
    //@FindBy(xpath = "//button[@class='ant-modal-close']")
    @FindBy(xpath = "//button[@aria-label='Close']")
    private WebElement closePopupButton;

    @FindBy(xpath = "//button[./span[@data-key='ninjachat.feature_tour_modal.close']]")
    private WebElement gotItPopupButton;
    public MainPage(WebBrowser webBrowser) {
        super(webBrowser);
        PageFactory.initElements(webBrowser.webDriver, this);
    }

    /**
     * @functionality close popup
     */
    public void closePopup() {
        WebDriverWait wait;

        wait = new WebDriverWait(webBrowser.webDriver, Duration.ofSeconds(TestProperties.browserPopupTimeout));
        try {
            gotItPopupButton = wait.until(ExpectedConditions.visibilityOf(gotItPopupButton));
        } catch (Exception exception) {
            /* popup couldn't be found */
        }
        /* check if popup is visible */
        if (gotItPopupButton != null) {
            gotItPopupButton.click();
        } else {
            /* popup didn't show up */
        }

    }
}
