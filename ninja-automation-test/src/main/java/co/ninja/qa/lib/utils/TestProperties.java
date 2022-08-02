package co.ninja.qa.lib.utils;

import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class TestProperties {
    public static final String rootDirectory = System.getProperty("user.dir");
    public static final String propertiesFilePath = "./config/test.properties";
    public static String logConfigurationFilePath;
    public static String loginEmail;
    public static String loginPassword;
    public static String browserType;
    public static String browserDriverPath;
    public static int browserPageLoadTimeout;
    public static int browserElementDetectTimeout;
    public static int browserPopupTimeout;
    public static String browserDownloadDirectory;
    public static int browserDownloadTimeout;
    public static String mainPageURL;
    public static String apiEndpoint;
    public static String reportDirectory;
    public static String centralReportDirectory;

    public static void setProperties() throws Exception {
        Properties properties;
        File propertiesFile;
        InputStream propertiesInputStream;

        properties = new Properties();
        propertiesFile = new File(propertiesFilePath);
        propertiesInputStream = new FileInputStream(propertiesFile);
        properties.load(propertiesInputStream);

        /* Set properties */
        /* report and logs properties */
        reportDirectory =
                properties.getProperty("report.directory")
                        + "/"
                        + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        centralReportDirectory = properties.getProperty("report.central.directory");
        logConfigurationFilePath = properties.getProperty("log.config.file");
        DOMConfigurator.configure(logConfigurationFilePath);

        /* browser properties */
        browserType = properties.getProperty("browser.type");
        browserDriverPath = properties.getProperty("browser.driver.path");
        /* Set env variable */
        if (browserType.equalsIgnoreCase("CHROME")) {
            /* Chrome */
            System.setProperty("webdriver.chrome.driver", browserDriverPath);
        } else if (browserType.equalsIgnoreCase("FIREFOX")) {
            /* Firefox */
            System.setProperty("webdriver.gecko.driver", browserDriverPath);
        } else if (browserType.equalsIgnoreCase("EDGE")) {
            /* Edge */
            System.setProperty("webdriver.edge.driver", browserDriverPath);
        } else {
            throw new Exception("Unsupported browser " + browserType);
        }
        browserPageLoadTimeout = Integer.parseInt(properties.getProperty("browser.page.load.timeout"));
        browserElementDetectTimeout =
                Integer.parseInt(properties.getProperty("browser.element.detect.timeout"));
        browserPopupTimeout = Integer.parseInt(properties.getProperty("browser.popup.timeout"));
        browserDownloadDirectory = properties.getProperty("browser.download.directory");
        browserDownloadDirectory = fixPath(browserDownloadDirectory);
        browserDownloadTimeout = Integer.parseInt(properties.getProperty("browser.download.timeout"));
        /* login properties */
        loginEmail = properties.getProperty("login.username");
        loginPassword = properties.getProperty("login.password");
        mainPageURL = properties.getProperty("main.page.url");
        apiEndpoint = properties.getProperty("api.endpoint");
    }

    /**
     *
     * @param path
     * @return
     */
    private static String fixPath(String path)
    {
        path = path.replaceAll("\\\\|/", "\\" + System.getProperty("file.separator"));
        if(Paths.get(path).isAbsolute() == false)
        {
            path = rootDirectory+path;
        }
        else {
            /* browserDownloadDirectory is absolute path */
        }
        return path;
    }
}
