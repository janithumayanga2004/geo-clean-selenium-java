package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.WaitUtils;
import java.time.Duration;

public class CleanerPage {
    private WebDriver driver;

    // --- Locators ---
    private By globalLoader = By.cssSelector("svg.animate-spin");
    private By staffRegistryLink = By.xpath("//aside//span[text()='Cleaners']/parent::a");
    private By registerNewBtn = By.xpath("//button[contains(.,'Register New Personnel')]");

    // Modal Fields (Registration)
    private By regNameField = By.xpath("//label[contains(.,'Personnel Full Name')]/following-sibling::input");
    private By regEmailField = By.xpath("//label[contains(.,'Secure Contact Email')]/following-sibling::input");
    private By regPhoneField = By.xpath("//label[contains(.,'Verified Phone Number')]/following-sibling::input");
    private By regPasswordField = By.xpath("//label[contains(.,'Initial Access Code')]/following-sibling::input");

    // Modal Fields (Update - Screenshot එකට අනුව)
    private By updateNameField = By.xpath("//label[text()='Full Name']/following-sibling::input");
    private By saveChangesBtn = By.xpath("//button[text()='Save Changes']");

    // Generic Buttons & Popups
    private By swalConfirmBtn = By.cssSelector(".swal2-confirm");

    public CleanerPage(WebDriver driver) {
        this.driver = driver;
    }

    private void waitForLoading() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(globalLoader));
        } catch (Exception e) { }
    }

    public void navigateToStaffRegistry() {
        waitForLoading();
        WaitUtils.waitForElementToBeClickable(driver, staffRegistryLink, 20).click();
    }

    public void registerCleaner(String name, String email, String phone, String password) {
        waitForLoading();
        WaitUtils.waitForElementToBeClickable(driver, registerNewBtn, 15).click();

        WaitUtils.waitForElementVisible(driver, regNameField, 10).sendKeys(name);
        driver.findElement(regEmailField).sendKeys(email);
        driver.findElement(regPhoneField).sendKeys(phone);
        driver.findElement(regPasswordField).sendKeys(password);

        try {

            WebElement onboardBtn = WaitUtils.waitForElementToBeClickable(driver,
                    By.xpath("//button[contains(translate(., 'AUTHORIZE', 'authorize'), 'authorize')]"), 10);
            onboardBtn.click();
        } catch (Exception e) {
            System.out.println("Standard click failed, attempting JS click on Authorize button...");
            WebElement onboardBtn = driver.findElement(By.xpath("//button[contains(.,'Authorize')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", onboardBtn);
        }
    }

    public void updateCleaner(String currentName, String newName) {
        waitForLoading();
        openActionsMenu(currentName);


        By editBtn = By.xpath("//button[contains(.,'Edit Profile')]");
        WebElement editElement = WaitUtils.waitForElementToBeClickable(driver, editBtn, 15);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editElement);


        WebElement nameInput = WaitUtils.waitForElementVisible(driver, updateNameField, 15);


        nameInput.click();
        nameInput.sendKeys(Keys.CONTROL + "a");
        nameInput.sendKeys(Keys.BACK_SPACE);
        nameInput.sendKeys(newName);


        try {
            WebElement saveBtn = WaitUtils.waitForElementToBeClickable(driver, saveChangesBtn, 10);
            saveBtn.click();
        } catch (Exception e) {
            System.out.println("Standard click failed on Save button, attempting JS click...");
            WebElement saveBtnAlt = driver.findElement(saveChangesBtn);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveBtnAlt);
        }


        closeSuccessPopup();
    }

    private void openActionsMenu(String cleanerName) {

        By moreBtnLocator = By.xpath("//p[contains(text(),'" + cleanerName + "')]/ancestor::tr//button[contains(@class, 'text-zinc-300')]");
        try {
            WebElement moreBtn = WaitUtils.waitForElementToBeClickable(driver, moreBtnLocator, 15);
            moreBtn.click();
        } catch (Exception e) {
            WebElement moreBtn = driver.findElement(moreBtnLocator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", moreBtn);
        }
        try { Thread.sleep(500); } catch (Exception e) {}
    }

    public void deleteCleaner(String name) {
        waitForLoading();
        openActionsMenu(name);

        By deleteBtn = By.xpath("//button[contains(.,'Delete Personnel')]");
        WaitUtils.waitForElementToBeClickable(driver, deleteBtn, 10).click();

        handleSwal(); // Confirm delete
        handleSwal(); // Success message
    }

    private void handleSwal() {
        try {
            WaitUtils.waitForElementToBeClickable(driver, swalConfirmBtn, 10).click();
            Thread.sleep(1000);
        } catch (Exception e) { }
    }

    public boolean isCleanerPresent(String name) {
        waitForLoading();
        try {
            By cleanerRow = By.xpath("//p[contains(text(),'" + name + "')]");
            return WaitUtils.waitForElementVisible(driver, cleanerRow, 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeSuccessPopup() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement okBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(@class, 'swal2-confirm') or text()='OK']")
            ));
            okBtn.click();
            System.out.println("Success popup එක සාර්ථකව වසා දැමුවා.");
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Success popup එක සොයා ගැනීමට නොහැකි විය.");
        }
    }
}