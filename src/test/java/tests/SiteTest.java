package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SitePage;

public class SiteTest extends BaseTest {

    @Test(priority = 1, description = "Test full Site Management lifecycle")
    public void testAddUpdateDeleteSite() {
        // 1. Navigation to Login
        driver.get("http://localhost:5173/login"); // Login page ekata yanna

        // 2. Authentication
        LoginPage loginPage = new LoginPage(driver);
        System.out.println("Logging in to the system...");
        loginPage.loginAsAdmin("janith16@geoclean.com", "Password3");

        // 3. Navigation to Sites
        SitePage sitePage = new SitePage(driver);
        System.out.println("Navigating to Sites section...");
        sitePage.navigateToSites();

        String initialSiteName = "Kandy";
        String updatedSiteName = "neluwa";

        // 4. CRUD Operations
        System.out.println("Step 1: Adding new site...");
        sitePage.addNewSite(initialSiteName);
        Assert.assertTrue(sitePage.isSitePresent(initialSiteName), "Site add une na!");

        System.out.println("Step 2: Updating site...");
        sitePage.updateSite(initialSiteName, updatedSiteName);
        Assert.assertTrue(sitePage.isSitePresent(updatedSiteName), "Site update une na!");

        System.out.println("Step 3: Deleting site...");
        sitePage.deleteSite(updatedSiteName);
        Assert.assertFalse(sitePage.isSitePresent(updatedSiteName), "Site delete une na!");

        System.out.println("All Site CRUD operations completed successfully!");
    }
}