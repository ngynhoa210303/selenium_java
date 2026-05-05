package tests.admin;
import components.admin.MenuBarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.admin.EmployeePage;
import utils.Config;

import java.time.Duration;
import java.util.List;

public class EmployeeSearchTest {

    WebDriver driver;
    LoginPage loginPage;
    EmployeePage employeePage;
    MenuBarComponent menuComponent;
    WebDriverWait wait;

    String baseUrl = Config.BASE_URL;
//    By notFoundTxt = By.xpath("//div[normalize-space(text())='Không có dữ liệu']");
    By adminOption = By.xpath("(//div[@role='option'])[2]");
    By customerOption = By.xpath("(//div[@role='option'])[4]");
    By employeeOption = By.xpath("(//div[@role='option'])[3]");
    By tableRows = By.xpath("//table//tbody//tr");
    By allBadgesRole = By.xpath("//tbody/tr/td[3]//span[@data-slot='badge']");
    By searchInput = By.xpath("//input[@data-slot='input']");
    By allOption = By.xpath("(//div[@role='option'])[1]");
    By employeeBadges = By.xpath("//table//tr//span[normalize-space()='Nhân viên']");

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login("hoanghuyen4@neyop.com", "Test@123");
        loginPage.verifyLoginSuccessMessage();
        menuComponent = new MenuBarComponent(driver);
        menuComponent.openCustomerMenu();
        menuComponent.goToUserList();
        employeePage = new EmployeePage(driver);
    }

    @Test
    public void TC38_SearchByEmail_Exact() {
        employeePage.search("maihien@neyop.com");
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 1));
    }

    @Test
    public void TC39_SearchByPhone_Exact() {
        employeePage.search("0988887679");
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 1));
    }

    @Test
    public void TC40_Search_NotFound() {
        employeePage.search("abcxyz123randomtest");
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 0));
    }

    @Test
    public void TC41_Filter_Admin() {
        employeePage.selectSearchRole(adminOption);
        By adminBadges = By.xpath("//table//tr//span[normalize-space()='Quản trị viên']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(adminBadges));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(adminBadges, 0));

        List<WebElement> badges = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(allBadgesRole)
        );

        boolean onlyNhanVien = badges.stream()
                .allMatch(e -> e.getText().trim().equals("Quản trị viên"));

        Assert.assertTrue(onlyNhanVien, "Danh sách chứa role khác Quản trị viên");
    }

    @Test
    public void TC42_Filter_Customer() {
        employeePage.selectSearchRole(customerOption);
        By customerBadges = By.xpath("//table//tr//span[normalize-space()='Khách hàng']");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(customerBadges));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(customerBadges, 0));

        List<WebElement> badges = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(allBadgesRole)
        );

        boolean onlyNhanVien = badges.stream()
                .allMatch(e -> e.getText().trim().equals("Khách hàng"));

        Assert.assertTrue(onlyNhanVien, "Danh sách chứa role khác Khách Hàng");
    }

    @Test
    public void TC43_Filter_Employee() {
        employeePage.selectSearchRole(employeeOption);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(employeeBadges));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(employeeBadges, 0));

        List<WebElement> badges = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(allBadgesRole)
        );

        boolean onlyNhanVien = badges.stream()
                .allMatch(e -> e.getText().trim().equals("Nhân viên"));

        Assert.assertTrue(onlyNhanVien, "Danh sách chứa role khác nhân viên");
    }

    @Test
    public void TC44_Filter_Role_And_Search() {
        employeePage.search("maihien@neyop.com");
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 1));
        employeePage.selectSearchRole(employeeOption);

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tableRows));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(employeeBadges));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(employeeBadges, 0));

        List<WebElement> badges = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(allBadgesRole)
        );

        boolean onlyNhanVien = badges.stream()
                .allMatch(e -> e.getText().trim().equals("Nhân viên"));

        Assert.assertTrue(onlyNhanVien, "Danh sách chứa role khác nhân viên");
    }

    @Test
    public void TC45_Filter_Status_All() {
        employeePage.selectStatus(allOption);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
    }

    @Test
    public void TC46_Combination_All() {
        employeePage.search("hanhuyen@neyop.com");
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 1));
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        driver.findElement(searchInput).clear();
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
        employeePage.selectSearchRole(adminOption);
        employeePage.selectStatus(allOption);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@class='ml-2 text-walnut-600']")));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
