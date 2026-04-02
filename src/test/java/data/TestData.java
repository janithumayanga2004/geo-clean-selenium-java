package data;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "adminLoginData")
    public Object[][] getAdminData() {
        return new Object[][] {
                // මෙතනට ඔයාට කැමති දත්ත ප්‍රමාණයක් දාන්න පුළුවන්
                {"janith@geoclean.com", "Password123!"}
        };
    }
}