# AutomationExercise

A Selenium-based automation testing framework for the [Automation Exercise](https://www.automationexercise.com/) web application, built using **Java**, **TestNG**, and **Page Object Model (POM)** design pattern.  
The project integrates **Extent Reports** for test reporting.

---

## 📂 Project Structure

```
src/test/java/org/AutomationExercise/e_com/
│
├── Base           # Base classes for WebDriver setup and teardown
├── TestCases      # Test scripts
├── Utils          # Utility classes (reporting, screenshot, config, etc.)
└── pageObjects    # Page Object Model classes for UI elements
```

---

## 🚀 Features

- **Java + Selenium WebDriver** for automation
- **TestNG** for test execution and assertions
- **Page Object Model (POM)** for maintainable test code
- **Extent Reports** for detailed HTML reports
- **Cross-browser testing** support (Chrome, Edge, Firefox)
- **Screenshot capture** for failed test cases
- **Maven** for build and dependency management

---

## 🛠 Tech Stack

- **Language:** Java
- **Test Framework:** TestNG
- **Automation Tool:** Selenium WebDriver
- **Design Pattern:** Page Object Model (POM)
- **Reporting:** Extent Reports
- **Build Tool:** Maven

---

## 📦 Setup & Installation

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/mukeshbalodi/AutomationExercise.git
cd AutomationExercise
```

### 2️⃣ Install Dependencies
Make sure **Java 11+** and **Maven** are installed:
```bash
mvn clean install
```

### 3️⃣ Configure Browser Drivers
This project uses **WebDriverManager**, so no manual driver setup is needed.

---

## ▶️ Running the Tests

Run all tests with:
```bash
mvn test
```

Run specific TestNG suite:
```bash
mvn test -DsuiteXmlFile=testngLocal.xml
```

---

## 📊 Reports

After test execution, reports are generated at:
```
target/surefire-reports/ExtentReport.html
```

---

## 📸 Screenshots

Screenshots of failed tests will be stored automatically in the designated folder inside the `target/surefire-reports/screenshots` directory.

---

## 🧑‍💻 Author

**Mukesh Balodi**  
📧 Email: *mukeshbalodi5@gmail.com*  
🔗 GitHub: [mukeshbalodi](https://github.com/mukeshbalodi)

---

