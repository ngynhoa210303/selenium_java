package tests.admin;

import components.admin.MenuBarComponent;
import org.openqa.selenium.*;
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

public class EmployeeCreateTest {

    WebDriver driver;
    LoginPage loginPage;
    EmployeePage employeePage;
    MenuBarComponent menuComponent;
    WebDriverWait wait;

    String baseUrl = Config.BASE_URL;
    String fullname_employee = "Nguyen Ha";
    String phone_employee = "0345677867";
    String email_employee = "n123nguyen@neyop.com";
    String employeeCode_employee = "EMP0999";
    String password_employee = "Test@123";
    String fullname_admin = "Nguyen Han Huyen";
    String phone_admin = "0986665434";
    String email_admin = "hanhuyen@neyop.com";
    String employeeCode_admin = "EMP0233";
    String password_admin = "Test@123";

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
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        menuComponent = new MenuBarComponent(driver);
        menuComponent.openCustomerMenu();
        menuComponent.goToUserList();
        employeePage = new EmployeePage(driver);
    }

    @Test
    public void TC28_InvalidPhone() {
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm("abc123", "Test User", "test1@gmail.com", "EMP001", "123456");
        employeePage.submitForm();

        Assert.assertEquals(
                employeePage.getPhoneErrorText(),
                "Số điện thoại không hợp lệ"
        );
    }

    @Test
    public void TC29_InvalidEmail() {
        employeePage.openAddEmployeeForm();
        employeePage.disableHtml5Validation();
        employeePage.fillEmployeeForm("0987654321", "Test User", "abc@", "EMP002", "123456");
        employeePage.submitForm();

        Assert.assertTrue(employeePage.isEmailErrorVisible());
    }

    @Test
    public void TC30_DuplicatePhone() {
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm("0987654321", "Test User", "test2@gmail.com", "EMP003", "123456");
        employeePage.submitForm();
        Assert.assertTrue(employeePage.isPhoneErrorVisible());
    }

    @Test
    public void TC31_DuplicateEmployeeCode() {
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm("0987654322", "Test User", "test3@gmail.com", "EMP001", "123456");
        employeePage.submitForm();
        Assert.assertTrue(employeePage.isCodeErrorVisible());
    }

    @Test
    public void TC32_DuplicateEmail() {
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm("0987654323", "Test User", "test1@gmail.com", "EMP005", "123456");
        employeePage.submitForm();
        Assert.assertTrue(employeePage.isEmailErrorVisible());
    }

    @Test
    public void TC33_EmptyAllFields() {
        employeePage.openAddEmployeeForm();
        employeePage.disableHtml5Validation();
        employeePage.submitForm();
        Assert.assertTrue(employeePage.isAllRequiredErrorsVisible());
    }

    @Test
    public void TC34_InvalidPassword() {
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm("0987654324", "Test User", "test7@gmail.com", "EMP007", "123");
        employeePage.submitForm();
        Assert.assertEquals(
                employeePage.getPasswordErrorText(),
                "Mật khẩu phải có ít nhất 6 ký tự"
        );
    }

    @Test
    public void TC35_InvalidFullName() {
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm("0987654325", "A", "test8@gmail.com", "EMP008", "123456");
        employeePage.submitForm();
        Assert.assertEquals(
                employeePage.getNameErrorText(),
                "Họ tên phải có ít nhất 2 ký tự"
        );
    }

    @Test
    public void TC36_AddValidEmployeeAccount() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@class='ml-2 text-walnut-600']")));
        if (employeePage.isEmployeeExist()) return;
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm(phone_employee, fullname_employee, email_employee, employeeCode_employee, password_employee);
        employeePage.selectRole("Nhân viên");
        employeePage.submitForm();
        employeePage.waitForCreateSuccess();
        employeePage.search(email_employee);
    }

    @Test
    public void TC37_AddValidAdminAccount() {
        employeePage.search(email_admin);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//span[@class='ml-2 text-walnut-600']")));
        if (employeePage.isEmployeeExist()) return;
        employeePage.openAddEmployeeForm();
        employeePage.fillEmployeeForm(phone_admin, fullname_admin, email_admin, employeeCode_admin, password_admin);
        employeePage.selectRole("Quản trị viên");
        employeePage.submitForm();
        employeePage.waitForCreateSuccess();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}