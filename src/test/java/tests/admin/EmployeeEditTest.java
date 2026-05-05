package tests.admin;

import components.admin.MenuBarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.LoginPage;
import pages.admin.EmployeePage;
import utils.Config;

import java.time.Duration;

public class EmployeeEditTest {
    WebDriver driver;
    LoginPage loginPage;
    EmployeePage employeePage;
    MenuBarComponent menuComponent;
    WebDriverWait wait;

    String baseUrl = Config.BASE_URL;
    String editFullname = "Nguyen Ha";
    String editPhone = "0978908777";
    String email_employee = "hanhna@gmail.com";
    String editEmployeeCode = "EMP12334";
    String email_admin = "hanhuyen@neyop.com";


    By nameError = By.xpath("//p[contains(text(),'Họ tên')]");
    By updateEmployeeSuccessToast = By.xpath("//div[contains(.,'Cập nhật thông tin nhân viên thành công')]");
    By tableRows = By.xpath("//table//tbody//tr");
    By roleCombobox = By.xpath("//label[@data-slot='form-label']/following-sibling::button[1]");
    By editBtn = By.xpath("(//table//tbody//tr//td[7]//button[4])[1]");
    By editDialog = By.xpath("//div[@role='dialog']");

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);

        driver = new EdgeDriver();
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get(baseUrl + "/login");
        loginPage = new LoginPage(driver);
        loginPage.login(email_admin, "Test@123");
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        menuComponent = new MenuBarComponent(driver);
        menuComponent.openCustomerMenu();
        menuComponent.goToUserList();
        employeePage = new EmployeePage(driver);
    }

    @Test
    public void TC47_InvalidPhone() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String oldPhone= employeePage.getData()[0];
        String oldName= employeePage.getData()[1];
        String oldCode= employeePage.getData()[2];
        employeePage.resetEditForm();
        employeePage.fillEmployeeEditForm("abc123", "Test User", "EMP0099");
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.cancelForm();
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String newPhone= employeePage.getData()[0];
        String newName= employeePage.getData()[1];
        String newCode= employeePage.getData()[2];
        Assert.assertEquals(newPhone, oldPhone, "Sai số điện thoại");
        Assert.assertEquals(newName, oldName,"Sai tên");
        Assert.assertEquals(newCode, oldCode, "Sai mã nhân viên");
    }

    @Test
    public void TC48_DuplicatePhone() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String oldPhone= employeePage.getData()[0];
        String oldName= employeePage.getData()[1];
        String oldCode= employeePage.getData()[2];
        employeePage.resetEditForm();
        employeePage.fillEmployeeEditForm("0987654321", "Test User", "EMP0099");
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.cancelForm();
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String newPhone= employeePage.getData()[0];
        String newName= employeePage.getData()[1];
        String newCode= employeePage.getData()[2];
        Assert.assertEquals(newPhone, oldPhone, "Sai số điện thoại");
        Assert.assertEquals(newName, oldName,"Sai tên");
        Assert.assertEquals(newCode, oldCode, "Sai mã nhân viên");
    }

    @Test
    public void TC49_DuplicateEmployeeCode() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String oldPhone= employeePage.getData()[0];
        String oldName= employeePage.getData()[1];
        String oldCode= employeePage.getData()[2];
        employeePage.resetEditForm();
        employeePage.fillEmployeeEditForm("0987654321", "Test User", "EMP001");
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.cancelForm();
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String newPhone= employeePage.getData()[0];
        String newName= employeePage.getData()[1];
        String newCode= employeePage.getData()[2];
        Assert.assertEquals(newPhone, oldPhone, "Sai số điện thoại");
        Assert.assertEquals(newName, oldName,"Sai tên");
        Assert.assertEquals(newCode, oldCode, "Sai mã nhân viên");
    }

    @Test
    public void TC50_EmptyAllFields() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        employeePage.resetEditForm();
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameError));
    }

    @Test
    public void TC51_InvalidFullName() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String oldPhone= employeePage.getData()[0];
        String oldName= employeePage.getData()[1];
        String oldCode= employeePage.getData()[2];
        employeePage.fillEmployeeEditForm("0987654321", "A", "EMP001");
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(nameError));
        Assert.assertEquals(error.getText().trim(), "Họ tên phải có ít nhất 2 ký tự");
        employeePage.cancelForm();
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String newPhone= employeePage.getData()[0];
        String newName= employeePage.getData()[1];
        String newCode= employeePage.getData()[2];
        Assert.assertEquals(newPhone, oldPhone, "Sai số điện thoại");
        Assert.assertEquals(newName, oldName,"Sai tên");
        Assert.assertEquals(newCode, oldCode, "Sai mã nhân viên");
    }

    @Test
    public void TC52_AdminToEmployee_Stable() {
        String email = "namtuan@gmail.com";

        // 1. Search & mở edit
        employeePage.search(email);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));

        // 2. SETUP: ép về Admin trước (đảm bảo state đúng)
        employeePage.selectRole("Quản trị viên");
        employeePage.submitForm();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(editDialog));
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateEmployeeSuccessToast));

        // 3. Mở lại để test chính
        employeePage.search(email);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));

        // 4. ACTION: Admin → Employee
        employeePage.selectRole("Nhân viên");
        employeePage.submitForm();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(editDialog));
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateEmployeeSuccessToast));

        // 5. VERIFY
        employeePage.search(email);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));

        String updatedRole = employeePage.getSelectedRole(roleCombobox);
        Assert.assertEquals(updatedRole, "Nhân viên", "Update role thất bại");

        // 6. ROLLBACK: về lại Admin
        employeePage.selectRole("Quản trị viên");
        employeePage.submitForm();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(editDialog));
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
    }
    @Test
    public void TC53_EmployeeToAdmin_Stable() {
        String email = "hungvan@neyop.com";

        // 1. Search & mở edit
        employeePage.search(email);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));

        // 2. SETUP: ép về Employee trước
        employeePage.selectRole("Nhân viên");
        employeePage.submitForm();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(editDialog));
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateEmployeeSuccessToast));

        // 3. Mở lại
        employeePage.search(email);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));

        // 4. ACTION: Employee → Admin
        employeePage.selectRole("Quản trị viên");
        employeePage.submitForm();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(editDialog));
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateEmployeeSuccessToast));

        // 5. VERIFY
        employeePage.search(email);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));

        String updatedRole = employeePage.getSelectedRole(roleCombobox);
        Assert.assertEquals(updatedRole, "Quản trị viên", "Update role thất bại");

        // 6. ROLLBACK: về lại Employee
        employeePage.selectRole("Nhân viên");
        employeePage.submitForm();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(editDialog));
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
    }
    @Test
    public void TC54_EditSuccess() {
        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
        String oldPhone= employeePage.getData()[0];
        String oldName= employeePage.getData()[1];
        String oldCode= employeePage.getData()[2];
        employeePage.resetEditForm();
        employeePage.fillEmployeeEditForm(editPhone, editFullname, editEmployeeCode);
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateEmployeeSuccessToast));
        System.out.println(oldPhone + oldName + oldCode);

        employeePage.search(email_employee);
        wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();

        String newPhone= employeePage.getData()[0];
        String newName= employeePage.getData()[1];
        String newCode= employeePage.getData()[2];
        Assert.assertEquals(newPhone, editPhone, "Sai số điện thoại");
        Assert.assertEquals(newName, editFullname,"Sai tên");
        Assert.assertEquals(newCode, editEmployeeCode,"Sai mã nhân viên");

        employeePage.resetEditForm();
        employeePage.fillEmployeeEditForm(oldPhone, oldName, oldCode);
        wait.until(ExpectedConditions.visibilityOfElementLocated(editDialog));
        employeePage.submitForm();
        wait.until(ExpectedConditions.visibilityOfElementLocated(updateEmployeeSuccessToast));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(updateEmployeeSuccessToast));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
