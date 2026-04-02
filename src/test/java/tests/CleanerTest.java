package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.CleanerPage;

public class CleanerTest extends BaseTest {

    @Test(priority = 1, description = "Test full Cleaner Management lifecycle")
    public void testCleanerLifecycle() {
        // 1. Login
        driver.get("http://localhost:5173/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin("janith17@geoclean.com", "Password4");

        // 2. Navigation
        CleanerPage cleanerPage = new CleanerPage(driver);
        System.out.println("Navigating to Staff Registry...");
        cleanerPage.navigateToStaffRegistry();

        String fullName = "sadun Perera";
        String updatedName = "sadun Silva";

        // 3. Register
        System.out.println("Registering new cleaner...");
        cleanerPage.registerCleaner(fullName, "sadun@test.com", "0772234567", "Cleaner@12334");


        System.out.println("Closing success notification...");
        cleanerPage.closeSuccessPopup();

        Assert.assertTrue(cleanerPage.isCleanerPresent(fullName), "Cleaner register une na!");

        // 4. Update
        System.out.println("Updating cleaner name...");
        cleanerPage.updateCleaner(fullName, updatedName);


        cleanerPage.closeSuccessPopup();

        Assert.assertTrue(cleanerPage.isCleanerPresent(updatedName), "Cleaner update une na!");


        System.out.println("Deleting cleaner...");
        cleanerPage.deleteCleaner(updatedName);


        cleanerPage.closeSuccessPopup();

        Assert.assertFalse(cleanerPage.isCleanerPresent(updatedName), "Cleaner delete une na!");

        System.out.println("Cleaner CRUD operations successful!");
    }
}