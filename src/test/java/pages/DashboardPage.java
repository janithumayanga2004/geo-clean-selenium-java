package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class DashboardPage {
    private WebDriver driver;

    private By dashboardHeader = By.xpath("//h1[contains(text(),'Command Center')]");
    private By logoutBtn = By.xpath("//span[contains(text(),'Secure Sign Out')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isDashboardLoaded() {
        return WaitUtils.waitForElementVisible(driver, dashboardHeader, 15).isDisplayed();
    }

    public void logout() {
        driver.findElement(logoutBtn).click();
    }
}