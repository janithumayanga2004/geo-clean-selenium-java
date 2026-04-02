package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import java.time.Duration;
import java.util.List;

public class AssignmentsPage {
    private WebDriver driver;

    // --- Locators ---
    private By globalLoader = By.cssSelector("svg.animate-spin");
    private By assignmentsLink = By.xpath("//aside//span[text()='Assignments']/parent::a");
    private By newDeploymentBtn = By.xpath("//button[contains(.,'New Deployment Node')]");

    // Modal Locators
    private By cleanerDropdown = By.xpath("//label[contains(.,'Personnel Selection')]/following-sibling::div/select");
    private By siteDropdown = By.xpath("//label[contains(.,'Facility Target')]/following-sibling::div/select");
    private By engageLinkBtn = By.xpath("//button[contains(.,'Engage Link')]");

    // Common Elements
    private By swalConfirmBtn = By.cssSelector(".swal2-confirm");

    public AssignmentsPage(WebDriver driver) {
        this.driver = driver;
    }

    private void waitForLoading() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(globalLoader));
        } catch (Exception e) { }
    }

    public void navigateToAssignments() {
        waitForLoading();
        WaitUtils.waitForElementToBeClickable(driver, assignmentsLink, 20).click();
    }

    public void createAssignment(String cleanerPartialName, String sitePartialName) {
        waitForLoading();
        WaitUtils.waitForElementToBeClickable(driver, newDeploymentBtn, 15).click();

        // Cleaner Dropdown (Partial Text Matching)
        WebElement cleanerEl = WaitUtils.waitForElementVisible(driver, cleanerDropdown, 10);
        selectDropdownByPartialText(cleanerEl, cleanerPartialName);

        // Site Dropdown  (Partial Text Matching)
        WebElement siteEl = driver.findElement(siteDropdown);
        selectDropdownByPartialText(siteEl, sitePartialName);

        // Form  submit
        try {
            driver.findElement(engageLinkBtn).click();
        } catch (Exception e) {
            WebElement engageBtn = driver.findElement(engageLinkBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", engageBtn);
        }
    }


    private void selectDropdownByPartialText(WebElement dropdownElement, String partialText) {
        Select select = new Select(dropdownElement);
        List<WebElement> options = select.getOptions();
        boolean found = false;

        for (WebElement option : options) {
            if (option.getText().toLowerCase().contains(partialText.toLowerCase())) {
                select.selectByVisibleText(option.getText());
                found = true;
                break;
            }
        }

        if (!found) {
            throw new NoSuchElementException("Dropdown එකේ '" + partialText + "' අඩංගු අගයක් සොයාගත නොහැකි විය.");
        }
    }

    public void toggleProtocolStatus(String cleanerName) {
        waitForLoading();
        By toggleBtn = By.xpath("//h4[contains(text(),'" + cleanerName + "')]/ancestor::div[contains(@class, 'card')]//button[contains(@title, 'Node')]");
        WaitUtils.waitForElementToBeClickable(driver, toggleBtn, 10).click();
    }

    public void terminateDeployment(String cleanerName) {
        waitForLoading();
        By trashBtn = By.xpath("//h4[contains(text(),'" + cleanerName + "')]/ancestor::div[contains(@class, 'card')]//button[contains(@class, 'bg-rose-50')]");
        WaitUtils.waitForElementToBeClickable(driver, trashBtn, 10).click();

        handleSwal(); // Confirm termination
        handleSwal(); // Success message
    }

    private void handleSwal() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, swalConfirmBtn, 10).click();
            Thread.sleep(1000);
        } catch (Exception e) { }
    }

    public boolean isDeploymentPresent(String cleanerName, String siteName) {
        waitForLoading();
        try {
            By assignmentCard = By.xpath("//h4[contains(text(),'" + cleanerName + "')]/ancestor::div[contains(@class, 'card')]//h4[contains(text(),'" + siteName + "')]");
            return WaitUtils.waitForElementVisible(driver, assignmentCard, 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}