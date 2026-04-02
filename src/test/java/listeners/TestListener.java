package listeners;

import base.BaseTest;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getName();
        System.out.println("Test Failed: " + testName + ". Taking screenshot...");

        // BaseTest එකේ තියෙන static driver එක මෙතනට ගන්නවා
        if (BaseTest.driver != null) {
            File srcFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
            try {
                // screenshots කියන folder එක නැත්නම් එක හදාගන්නවා
                File screenshotDir = new File("screenshots");
                if (!screenshotDir.exists()) screenshotDir.mkdirs();

                FileHandler.copy(srcFile, new File("screenshots/" + testName + ".png"));
                System.out.println("Screenshot saved to: screenshots/" + testName + ".png");
            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }
        } else {
            System.out.println("Driver is null, cannot take screenshot.");
        }
    }
}