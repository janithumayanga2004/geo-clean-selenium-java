package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import java.time.Duration;

public class SitePage {
    private WebDriver driver;

    private By globalLoader = By.cssSelector("svg.animate-spin");
    private By sitesSidebarLink = By.xpath("//aside//span[text()='Sites']/parent::a | //aside//a[.//span[text()='Sites']]");
    private By establishNewSiteBtn = By.xpath("//button[contains(.,'Establish New Site')]");
    private By siteNameField = By.cssSelector("input.input-field");
    private By leafletMap = By.className("leaflet-container");
    // SitePage.java හි මෙය වෙනස් කරන්න
    // SitePage.java හි variable එක මෙසේ වෙනස් කරන්න
    private By submitModalBtn = By.xpath("//button[@type='submit' and (contains(.,'Deploy Node') or contains(.,'Synchronize Data'))]");
    private By swalOKBtn = By.cssSelector(".swal2-confirm");

    public SitePage(WebDriver driver) {
        this.driver = driver;
    }

    private void waitForLoading() {
        try {
            // Loader eka nathiwena kan inna ona (Invisibility)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(globalLoader));
        } catch (Exception e) {
            // Loader nathi welawaka timeout wenna nodi inna
        }
    }

    public void navigateToSites() {
        waitForLoading();
        WebElement link = WaitUtils.waitForElementToBeClickable(driver, sitesSidebarLink, 25);
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        waitForLoading();
    }

    public void addNewSite(String siteName) {
        waitForLoading();
        WaitUtils.waitForElementToBeClickable(driver, establishNewSiteBtn, 20).click();
        fillSiteForm(siteName);
    }

    public void updateSite(String oldName, String newName) {
        waitForLoading();

        By editBtn = By.xpath("//h3[contains(text(),'" + oldName + "')]/parent::div//button[contains(@class, 'text-zinc-400')]");

        WaitUtils.waitForElementToBeClickable(driver, editBtn, 20).click();
        fillSiteForm(newName);
    }

    public void deleteSite(String siteName) {
        waitForLoading();

        By deleteBtn = By.xpath("//h3[contains(text(),'" + siteName + "')]/parent::div//button[contains(@class, 'text-rose-400')]");

        WaitUtils.waitForElementToBeClickable(driver, deleteBtn, 15).click();
        handleSwal();
    }

    private void fillSiteForm(String name) {
        // 1. Site Name
        WebElement input = WaitUtils.waitForElementVisible(driver, siteNameField, 15);
        input.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        input.sendKeys(name);

        // 2. Map  click
        try {
            WebElement mapElement = driver.findElement(leafletMap);

            new Actions(driver).moveToElement(mapElement).moveByOffset(15, 15).click().perform();
            System.out.println("Map clicked to select location.");


            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("Map interaction failed: " + e.getMessage());
        }

        // 3. Submit Button එක click

        try {
            WebElement btn = WaitUtils.waitForElementToBeClickable(driver, submitModalBtn, 20);
            btn.click();
        } catch (Exception e) {
            WebElement btn = driver.findElement(submitModalBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        }

        handleSwal();
    }

    private void handleSwal() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, swalOKBtn, 10).click();
            Thread.sleep(1000);
        } catch (Exception e) { }
    }

    public boolean isSitePresent(String name) {
        waitForLoading();
        try {

            By siteHeading = By.xpath("//h3[contains(text(),'" + name + "')]");
            return WaitUtils.waitForElementVisible(driver, siteHeading, 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}