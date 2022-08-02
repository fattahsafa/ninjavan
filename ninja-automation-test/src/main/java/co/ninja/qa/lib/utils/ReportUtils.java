package co.ninja.qa.lib.utils;

import co.ninja.qa.lib.web.WebBrowser;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
;

public class ReportUtils {

  private ExtentTest extentTest;
  private ExtentReports extentReporter;
  private ExtentSparkReporter htmlReporter;
  private String reportDirectory;
  private String latestReportDirectory;

  public ReportUtils(String reportDirectoryPath, String reportCentralDirectoryPath) {
    String reportFilePath;
    File reportTimestamppedDirectory;
    File screenshotDirectory;
    File latestReportDirectory;

    /* Create directories */
    reportTimestamppedDirectory = new File(reportDirectoryPath);
    reportTimestamppedDirectory.getParentFile().mkdirs();
    reportTimestamppedDirectory.mkdir();
    screenshotDirectory = new File(reportDirectoryPath + "/screenshots");
    screenshotDirectory.mkdir();
    this.reportDirectory = reportDirectoryPath;

    /* latest_report_directory */
    latestReportDirectory = new File(reportCentralDirectoryPath);
    latestReportDirectory.delete();
    latestReportDirectory.mkdir();
    this.latestReportDirectory = reportCentralDirectoryPath;

    reportFilePath = reportTimestamppedDirectory + "/" + "test-report.html";

    // initialize the HtmlReporter
    htmlReporter = new ExtentSparkReporter(reportFilePath);

    // initialize ExtentReports and attach the HtmlReporter
    extentReporter = new ExtentReports();
    extentReporter.attachReporter(htmlReporter);

    // configuration items to change the look and feel
    // add content, manage tests etc
    // htmlReporter.config().setChartVisibilityOnOpen(true);
    htmlReporter.config().setDocumentTitle("Automation Test Results");
    htmlReporter.config().setReportName("Test Report");
    // htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
    htmlReporter.config().setTheme(Theme.STANDARD);
    htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
  }

  /**
   * @param webBrowser
   * @param reportDirectory
   * @return
   * @throws IOException
   */
  public static String captureScreenshot(WebBrowser webBrowser, String reportDirectory) {
    String screenshotDirectory = reportDirectory + "/screenshots";
    String screenshotPath = screenshotDirectory + "/" + Math.random() + ".png";
    File screenshotSourceFile;

    screenshotSourceFile =
        ((TakesScreenshot) webBrowser.webDriver).getScreenshotAs(OutputType.FILE);
    try {
      Files.copy(
          Path.of(screenshotSourceFile.getPath()),
          Path.of(screenshotPath),
          StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return screenshotPath;
  }

  public void addTest(String testName) {
    this.extentTest = extentReporter.createTest(testName);
  }

  public void addTestLog(Status status, String comment) {
    extentTest.log(status, comment);
  }

  public void attachScreenshot(String screenshotPath) {
    screenshotPath = screenshotPath.replace(reportDirectory, "./");
    extentTest.addScreenCaptureFromPath(screenshotPath);
  }

  /** */
  public void closeReport() {
    extentReporter.flush();
  }

  /**
   * @throws IOException
   */
  public void markAsLatest() throws IOException {
    FileUtils.copyDirectory(new File(reportDirectory), new File(latestReportDirectory));
  }
}
