package co.ninja.qa.pages;

import co.ninja.qa.lib.web.WebBrowser;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    private static final Logger logger = Logger.getLogger(LoginPage.class);

    @FindBy(id = "email")
    private WebElement emailTextField;

    @FindBy(id = "password")
    private WebElement passwordTextField;

    @FindBy(xpath = "//button[.//span[text()='Login']]")
    private WebElement loginButton;


    public LoginPage(WebBrowser webBrowser) {
        super(webBrowser);
        PageFactory.initElements(webBrowser.webDriver, this);
    }

    /**
     * @param email
     * @param password
     * @functionality sing in using email and password
     */
    public void signIn(String email, String password) {
        /* step 1: type email */
        logger.debug("Type <" + email + "> in the email test field");
        webBrowser.setText(emailTextField, email);
        /* step 2: password */
        logger.debug("decode the password (if needed)");
        logger.debug("Type <"+password+"> in the password test field");
        webBrowser.setText(passwordTextField, password);
        /* step 3: click login */
        logger.debug("Click on the login button");
        webBrowser.click(loginButton);
    }

}
