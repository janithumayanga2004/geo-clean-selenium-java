package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.DashboardPage;

public class DashboardTest extends BaseTest {

    @Test(priority = 3, description = "Verify Dashboard UI elements and Logout")
    public void testDashboardOverview() {

        driver.get("http://localhost:5173/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin("janith16@geoclean.com", "Password3");

        DashboardPage dashboardPage = new DashboardPage(driver);


        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard Command Center Header not visible!");


        dashboardPage.logout();
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Logout failed!");
    }
}