package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.AssignmentsPage;

public class AssignmentsTest extends BaseTest {

    @Test(priority = 1, description = "Test Assignments (Deployment Matrix) full lifecycle with partial text matching")
    public void testAssignmentLifecycle() {
        // 1. Login
        driver.get("http://localhost:5173/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin("janith16@geoclean.com", "Password3");

        // 2. Navigation
        AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
        System.out.println("Navigating to Deployment Matrix...");
        assignmentsPage.navigateToAssignments();

        // 3. Setup Data

        String cleaner = "Jake Cleaner2";
        String site = "udugama";

        // 4. Create (Engage Link)
        System.out.println("Engaging new deployment link for: " + cleaner);
        assignmentsPage.createAssignment(cleaner, site);
        Assert.assertTrue(assignmentsPage.isDeploymentPresent(cleaner, site), "Deployment එක නිර්මාණය වුණේ නැත!");

        // 5. Toggle Status
        System.out.println("Toggling protocol status for: " + cleaner);
        assignmentsPage.toggleProtocolStatus(cleaner);
        try { Thread.sleep(2000); } catch (Exception e) {}

        // 6. Delete (Terminate)
        System.out.println("Terminating deployment protocol...");
        assignmentsPage.terminateDeployment(cleaner);
        Assert.assertFalse(assignmentsPage.isDeploymentPresent(cleaner, site), "Deployment එක සාර්ථකව ඉවත් වුණේ නැත!");

        System.out.println("Assignments (Deployment Matrix) automation successful!");
    }
}