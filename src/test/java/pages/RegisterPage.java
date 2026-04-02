package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class RegisterPage {
    private WebDriver driver;

    // Locators
    private By fullNameField = By.name("name");
    private By emailField = By.name("email");
    private By phoneField = By.name("phone");
    private By passwordField = By.name("password");
    private By adminSecretField = By.name("adminSecret"); // Confirm this name in React!

    // contains(., '...')
    private By registerBtn = By.xpath("//button[contains(.,'Register Admin')]");


    private By successMessage = By.xpath("//h2[contains(.,'Admin Created!')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void registerAdmin(String name, String email, String phone, String password, String secret) {

        WaitUtils.waitForElementVisible(driver, fullNameField, 10).sendKeys(name);

        driver.findElement(emailField).sendKeys(email);
        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(adminSecretField).sendKeys(secret);


        driver.findElement(registerBtn).click();
    }

    public boolean isRegistrationSuccess() {
        try {
            return WaitUtils.waitForElementVisible(driver, successMessage, 15).isDisplayed();
        } catch (Exception e) {
            System.out.println("Success message not found: " + e.getMessage());
            return false;
        }
    }
}