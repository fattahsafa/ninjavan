package co.ninja.qa.lib.web;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebBrowser {

    private final String downloadDirectory;
    private final int downloadTimeout;
    public WebDriver webDriver;

    public WebBrowser(String driverType, int elementDetectTimeout, int pageLoadTimeout, String downloadDirectory, int downloadTimeout)
            throws Exception {
        this.downloadDirectory = downloadDirectory;
        this.downloadTimeout = downloadTimeout;

        if (driverType.equalsIgnoreCase("CHROME")) {
            /* Chrome */
            ChromeOptions options = new ChromeOptions();
            HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
            chromeOptionsMap.put("plugins.plugins_disabled", new String[] { "Chrome PDF Viewer" });
            chromeOptionsMap.put("plugins.always_open_pdf_externally", true);
            chromeOptionsMap.put("download.default_directory",downloadDirectory);
            options.setExperimentalOption("prefs", chromeOptionsMap);
            webDriver = new ChromeDriver(options);

        } else if (driverType.equalsIgnoreCase("FIREFOX")) {
            /* Firefox */
            /* set options */
            FirefoxOptions firefoxOptions;
            FirefoxProfile profile = new FirefoxProfile();

            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.download.dir", downloadDirectory);
            profile.setPreference("browser.download.useDownloadDir", true);
            profile.setPreference("pdfjs.disabled", true);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/pdf");
            profile.setPreference("browser.helperApps.neverAsk.openFile","application/pdf");
            firefoxOptions = new FirefoxOptions();
            firefoxOptions.setProfile(profile);

            webDriver = new FirefoxDriver(firefoxOptions);
        } else if (driverType.equalsIgnoreCase("EDGE")) {
            /* Edge */
            EdgeOptions edgeOptions;

            edgeOptions = new EdgeOptions();
            HashMap<String, Object> edgeOptionsMap = new HashMap<String, Object>();
            edgeOptionsMap.put("download.prompt_for_download", false);
            edgeOptionsMap.put("plugins.always_open_pdf_externally", true);
            edgeOptionsMap.put("download.default_directory",downloadDirectory);
            edgeOptions.setExperimentalOption("prefs", edgeOptionsMap);

            webDriver = new EdgeDriver(edgeOptions);

        } else {
            throw new Exception("Unsupported driver " + driverType);
        }
        /* Set timeouts */
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(elementDetectTimeout));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        /* Maximize */
        webDriver.manage().window().maximize();
        /* delete all cookies */
        webDriver.manage().deleteAllCookies();
    }

    /**
     * Methods to perform actions on web elements *
     */
    public void openPage(String url) {
        webDriver.get(url);
    }

    public void clickImage(WebElement element) {
        Actions actions;

        actions = new Actions(webDriver);
        actions.moveToElement(element).click().perform();
    }

    public void click(WebElement element) {
        element.click();
    }

    public void clickR(WebElement element) {
        Actions actions;

        actions = new Actions(webDriver);
        actions.contextClick(element);
    }

    public void clickDbl(WebElement element) {
        Actions actions;

        actions = new Actions(webDriver);
        actions.doubleClick(element);
    }

    public void clear(WebElement element) {
        element.clear();
    }

    public void setText(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public void selectOption(WebElement element, String option) {
        Select select;

        select = new Select(element);
        select.selectByVisibleText(option);
    }

    public void selectCheckBox(WebElement element) {
        if (element.isSelected()) {

        } else {
            element.click();
        }
    }

    public void deselectCheckBox(WebElement element) {
        if (!element.isSelected()) {

        } else {
            element.click();
        }
    }

    public void acceptAlert() {
        webDriver.switchTo().alert().accept();
    }

    public String getAlertText() {
        return webDriver.switchTo().alert().getText();
    }

    public void dismissAlert() {
        webDriver.switchTo().alert().dismiss();
    }

    public void closeTab() {
        webDriver.close();
    }

    public void quit() {
        webDriver.quit();
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public void mouseHover(WebElement webElement) {
        Actions action = new Actions(webDriver);

        action.moveToElement(webElement).perform();
    }

    public void scrollToPageBottom() {
        webDriver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL, Keys.END);
    }

    /**
     *
     * @return
     */
    private String[] getOpenedTabs()
    {
        List<String> openedTabs;

        openedTabs = new ArrayList<String>(webDriver.getWindowHandles());
        return openedTabs.toArray(new String[0]);
    }

    /**
     *
     * @param tabIndex
     */
    public void switchToTab(int tabIndex)
    {
        String[] openedTabs;

        openedTabs = getOpenedTabs();
        webDriver.switchTo().window(openedTabs[tabIndex]);
    }
    /**
     *
     * @return
     */
    public void switchToFirstTab()
    {
        String[] openedTabs;
        int tabIndex;

        /* get opened tabs */
        openedTabs = getOpenedTabs();
            webDriver.switchTo().window(openedTabs[0]);
    }

    /**
     *
     * @return
     */
    public void switchToLastTab()
    {
        String[] openedTabs;

        /* get opened tabs */
        openedTabs = getOpenedTabs();
        webDriver.switchTo().window(openedTabs[openedTabs.length-1]);
    }

    /**
     *
     * @return
     * @throws IOException
     * @functionality extract PDF text
     */
    public String extractTextFromPDF(String uri) throws IOException {
        InputStream inputStream;
        URL pdfUrl;
        File pdfFile;
        PDDocument pdfDocument;
        String content;

        if( Files.exists(Path.of(uri)))
        {
            /* path */
            pdfFile = new File(uri);
            inputStream = new FileInputStream(pdfFile);
        }
        else {
            /* url */
            pdfUrl = new URL(uri);
            inputStream = pdfUrl.openStream();
        }
        pdfDocument = Loader.loadPDF(inputStream);
        content = new PDFTextStripper().getText(pdfDocument);
        pdfDocument.close();
        inputStream.close();

        return content;
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
    public String waitUntilFileDownloaded(String fileName){
        Path filePath;

        filePath = Paths.get(downloadDirectory, fileName);
        new WebDriverWait(webDriver, Duration.ofSeconds(downloadTimeout)).until(d -> filePath.toFile().exists());

        return filePath.toString();
    }

}
