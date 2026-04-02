package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;

public class RegisterTest extends BaseTest {

    @Test(priority = 1, description = "Verify Admin Registration with valid details")
    public void testAdminRegistration() {
        driver.get("http://localhost:5173/register-admin");
        RegisterPage registerPage = new RegisterPage(driver);


        registerPage.registerAdmin("Janith Admin", "janith17@geoclean.com", "0771238567", "Password4", "super_secret_key_123");

        boolean isSuccess = registerPage.isRegistrationSuccess();
        Assert.assertTrue(isSuccess, "Registration Success message is not displayed!");
    }
}