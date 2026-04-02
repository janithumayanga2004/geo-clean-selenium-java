package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class LoginPage {
    private WebDriver driver;

    private By emailField = By.xpath("//input[@type='text']");
    private By passwordField = By.xpath("//input[@type='password']");
    private By loginBtn = By.xpath("//button[contains(text(),'Connect Device')]");
    private By adminRoleBtn = By.xpath("//button[contains(text(),'Admin')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void loginAsAdmin(String email, String password) {

        WaitUtils.waitForElementToBeClickable(driver, adminRoleBtn, 5).click();

        WaitUtils.waitForElementVisible(driver, emailField, 10).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginBtn).click();
    }
}