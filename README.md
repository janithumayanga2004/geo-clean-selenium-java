# Geo Clean Automation Testing (Selenium)

## 📌 Project Overview

This project contains automated test scripts for the **Geo Clean Cleaner Tracking System** using Selenium.
The system allows admins to manage sites and cleaners, while cleaners can check-in and check-out using QR codes, GPS validation, and selfie verification.

---

## 🎯 Objective

To automate critical user flows of the system and ensure reliability, accuracy, and performance using industry-standard QA practices.

---

## 🧪 Automated Test Coverage

The following core functionalities are automated:

* ✅ OTP Login Validation
* ✅ QR Code Scanning Flow
* ✅ Cleaner Check-in Process
* ✅ Cleaner Check-out Process
* ✅ GPS Validation (mock/logic level)
* ✅ Admin Login & Dashboard Access
* ✅ Attendance Report Verification

---

## 🛠️ Technologies Used

* **Language:** Java
* **Automation Tool:** Selenium WebDriver
* **Build Tool:** Maven
* **Testing Framework:** TestNG
* **IDE:** IntelliJ IDEA
* **Browser:** Chrome (ChromeDriver)

---

## 📂 Project Structure

```
src/
 ├── main/
 └── test/
     ├── testcases/
     ├── pages/
     ├── utils/
     └── base/
```

* `testcases/` → Test scripts
* `pages/` → Page Object Model (POM) classes
* `utils/` → Helper functions
* `base/` → Setup & configuration

---

## 🚀 How to Run the Project

1. Clone the repository:

```
git clone https://github.com/your-username/geo-clean-automation.git
```

2. Open project in IntelliJ IDEA

3. Install dependencies:

```
mvn clean install
```

4. Run tests:

```
mvn test
```

---

## 📊 Test Strategy

* Risk-based testing approach
* Focus on critical business flows
* Positive & negative scenarios covered
* Reusable Page Object Model (POM) design

---

## 🐞 Reporting

* Test results generated via TestNG reports
* Failures can be analyzed using logs and screenshots

---

## 📌 Future Improvements

* Integrate with CI/CD (GitHub Actions / Jenkins)
* Add API automation testing (Postman / RestAssured)
* Implement mobile automation using Appium
* Add advanced reporting (Extent Reports)

---

## 👨‍💻 Author

**Janith Umayanga**
QA Intern | Automation Tester

---

## 💬 Note

This project demonstrates industry-level automation practices including structured test design, maintainable framework setup, and scalable test execution.
