package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ReportsPage;
import java.time.LocalDate;

public class ReportsTest extends BaseTest {

    @Test(priority = 1, description = "Verify Intelligence Logs summary and data loading")
    public void testReportsFunctionality() {
        // 1. Login
        driver.get("http://localhost:5173/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin("janith17@geoclean.com", "Password4");

        // 2. Navigation
        ReportsPage reportsPage = new ReportsPage(driver);
        System.out.println("Navigating to Reports Page...");
        reportsPage.navigateToReports();

        // 3. Verify Summary Chips
        Assert.assertTrue(reportsPage.isSummaryDisplayed(), "Summary chips ");
        System.out.println("Current Stats: " + reportsPage.getSummaryStats());

        // 4. Date Filter Test (අද දිනය සඳහා)
        String today = LocalDate.now().toString();
        System.out.println("Selecting date: " + today);
        reportsPage.selectDate(today);

        // 5. Refresh Test
        System.out.println("Refreshing report data...");
        reportsPage.clickRefresh();

        // 6. Section Expansion & Cleaner Visibility

        String testCleaner = "Jake Cleaner2";
        reportsPage.ensureSectionExpanded("Not Checked In");

        boolean isFound = reportsPage.isCleanerVisibleInList("Not Checked In", testCleaner);
        System.out.println("Cleaner '" + testCleaner + "' found in Not Checked In list: " + isFound);

        System.out.println("Reports (Intelligence Logs) Automation Completed Successfully!");
    }
}