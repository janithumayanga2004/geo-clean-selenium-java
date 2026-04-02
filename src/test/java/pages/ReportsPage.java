package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import java.time.Duration;

public class ReportsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By globalLoader = By.cssSelector("svg.animate-spin");
    private By reportsLink = By.xpath("//aside//span[text()='Reports']/parent::a");
    private By datePicker = By.id("report-date-picker");
    private By refreshBtn = By.xpath("//button[contains(.,'Refresh')]");

    // Summary Chips
    private By totalChip = By.xpath("//span[contains(.,'Total')]");
    private By notArrivedChip = By.xpath("//span[contains(.,'not arrived')]");
    private By onSiteChip = By.xpath("//span[contains(.,'on-site')]");
    private By completedChip = By.xpath("//span[contains(.,'completed')]");

    // Section Headers - UI Structure
    private By notCheckedInSection = By.xpath("//p[contains(text(),'Not Checked In')]/ancestor::button");
    private By currentlyOnSiteSection = By.xpath("//p[contains(text(),'Currently On Site')]/ancestor::button");
    private By shiftCompletedSection = By.xpath("//p[contains(text(),'Shift Completed')]/ancestor::button");

    public ReportsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void waitForLoading() {
        try {

            wait.until(ExpectedConditions.invisibilityOfElementLocated(globalLoader));
        } catch (Exception e) {

        }
    }

    public void navigateToReports() {
        waitForLoading();
        WebElement link = WaitUtils.waitForElementToBeClickable(driver, reportsLink, 25);
        link.click();
        waitForLoading();
    }

    public void selectDate(String date) {
        WebElement dateInput = WaitUtils.waitForElementVisible(driver, datePicker, 15);
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);
        waitForLoading();
    }

    public void clickRefresh() {
        WaitUtils.waitForElementToBeClickable(driver, refreshBtn, 15).click();
        waitForLoading();
    }

    // --- Validation Methods ---

    public boolean isSummaryDisplayed() {
        waitForLoading();
        return WaitUtils.waitForElementVisible(driver, totalChip, 15).isDisplayed();
    }

    public String getSummaryStats() {
        waitForLoading();
        return "Total: " + driver.findElement(totalChip).getText() +
                " | Not Arrived: " + driver.findElement(notArrivedChip).getText() +
                " | On-Site: " + driver.findElement(onSiteChip).getText() +
                " | Completed: " + driver.findElement(completedChip).getText();
    }


    public void ensureSectionExpanded(String sectionName) {
        By sectionLocator;
        if (sectionName.equalsIgnoreCase("Not Checked In")) sectionLocator = notCheckedInSection;
        else if (sectionName.equalsIgnoreCase("On Site")) sectionLocator = currentlyOnSiteSection;
        else sectionLocator = shiftCompletedSection;


        WebElement sectionHeader = WaitUtils.waitForElementToBeClickable(driver, sectionLocator, 15);
        sectionHeader.click();
        System.out.println("Section '" + sectionName + "' clicked.");

        // 2. "Personnel" header

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//th[contains(text(), 'Personnel')]")
            ));
            System.out.println("Table header 'Personnel' found.");
        } catch (TimeoutException e) {
            System.out.println("Personnel header not found, checking for table rows...");

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody/tr")));
        }
    }



    public boolean isCleanerVisibleInList(String sectionName, String cleanerName) {
        waitForLoading();


        String xpath = "//*[normalize-space(text())='" + cleanerName + "']";

        try {

            WebElement cleanerElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            boolean isVisible = cleanerElement.isDisplayed();
            return isVisible;
        } catch (Exception e) {

            System.out.println("DEBUG: Could not find cleaner with name: " + cleanerName);
            return false;
        }


    }
}