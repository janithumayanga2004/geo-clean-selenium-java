package tests;

import base.BaseTest;
import data.TestData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.DashboardPage;

public class LoginTest extends BaseTest {


    @Test(priority = 2, dataProvider = "adminLoginData", dataProviderClass = TestData.class,
            description = "Verify Admin Login with dynamic data")
    public void testAdminLogin(String email, String password) {
        driver.get("http://localhost:5173/login");
        LoginPage loginPage = new LoginPage(driver);

        loginPage.loginAsAdmin(email, password);

        DashboardPage dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Failed to redirect to Dashboard after login!");
    }
}