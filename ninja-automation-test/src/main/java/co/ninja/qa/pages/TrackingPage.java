package co.ninja.qa.pages;

import co.ninja.qa.lib.web.WebBrowser;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class TrackingPage extends BasePage {
    private static final Logger logger = Logger.getLogger(TrackingPage.class);

    @FindBy(xpath = "//button[.//span[text()='Search']]")
    private WebElement searchButton;
    @FindBy(xpath = "//input[@data-key='search_input_placeholder']")
    private WebElement searchTextField;

    @FindBy(xpath = "//*[contains(@class,'OrderTable')]")
    private WebElement orderTable;


    public TrackingPage(WebBrowser webBrowser) {
        super(webBrowser);
        PageFactory.initElements(webBrowser.webDriver, this);
    }

    /**
     * @functionality search by keyword
     */
    public void search(String keyword) {
        logger.debug("Search for <" + keyword + ">");
        logger.debug("step1: Type <" + keyword + "> in the search box");
        webBrowser.setText(searchTextField, keyword);
        logger.debug("step2: click on the search button");
        webBrowser.click(searchButton);
    }

    /**
     * @param rowIndex
     */
    public String getOrderTrackingID(int rowIndex) {
        final String columnName = "Requested Tracking ID";
        WebElement row;
        WebElement column;
        String trackingID;

        logger.debug("Get order tracking ID of the row with index= " + rowIndex);
        logger.debug("Get column index of column " + columnName);
        row = getOrderRow(rowIndex);
        column = getOrderColumn(row, columnName);
        trackingID = Jsoup.parse(column.getAttribute("innerHTML")).text();
        logger.debug("Tracking ID = " + trackingID);
        return trackingID;
    }

    /**
     * @param rowIndex
     */
    public void clickOrderTrackingID(int rowIndex) {
        final String columnName = "Requested Tracking ID";
        logger.info("Click on the <" + columnName + "> of the row with index= " + rowIndex);
        clickOrderRow(rowIndex, columnName);
        webBrowser.switchToLastTab();

    }

    /**
     * @param rowIndex
     * @param columnName
     */
    private void clickOrderRow(int rowIndex, String columnName) {
        WebElement row;
        WebElement column;

        logger.debug("Click on the row with index=" + rowIndex + " in the orders table");

        /* find row */
        row = getOrderRow(rowIndex);
        /* find column. Index is incremented by 1 since the column are 1-based index */
        column = getOrderColumn(row, columnName);
        webBrowser.click(column);
    }

    /**
     * @param row
     * @param columnName
     * @return
     */
    private WebElement getOrderColumn(WebElement row, String columnName) {
        WebElement column;
        String columnXPath;
        int columnIndex;

        /* find column  index */
        logger.debug("Get column index of column " + columnName);

        columnIndex = getOrderTableColumnIndex(columnName);
        logger.debug("Column index = " + columnIndex);

        columnXPath = String.format("//*[@role='gridcell' and @aria-colindex='%d']", columnIndex + 1);
        column = row.findElement(By.xpath(columnXPath));

        return column;
    }

    /**
     * @param rowIndex
     * @return
     */
    private WebElement getOrderRow(int rowIndex) {
        WebElement row;
        String rowXPath;

        rowXPath = String.format("//*[@role='row' and @aria-rowindex='%d']", rowIndex);
        row = orderTable.findElement(By.xpath(rowXPath));

        return row;
    }

    /**
     * @param columnName
     * @return
     */
    private int getOrderTableColumnIndex(String columnName) {
        List<WebElement> headerColumns;
        int columnIndex;
        boolean columnFound;


        columnFound = false;
        /* find column  index */
        columnIndex = 0;
        headerColumns = orderTable.findElements(By.xpath("//*[@role='columnheader']"));
        /* loop over columns */
        for (WebElement headerColumn : headerColumns) {
            if (headerColumn.getAttribute("innerHTML").contains(columnName)) {
                columnFound = true;
                break;
            }
            columnIndex++;
        }

        /* check if column found */
        if (!columnFound) {
            /* column not found */
            columnIndex = -1;
        } else {
            /* column found */
        }
        return columnIndex;
    }
}
