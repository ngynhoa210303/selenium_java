package tests.customer;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.customer.RegisterPage;
import utils.Config;

import java.time.Duration;

public class RegisterTest {

    WebDriver driver;
    WebDriverWait wait;
    RegisterPage registerPage;

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(Config.BASE_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        registerPage = new RegisterPage(driver);
        registerPage.openRegister();
    }

    @Test
    public void TC01_Register_WrongFormatPhone() {
        registerPage.fillForm("Test User", "tc01@gmail.com", "098111", "testaddress", "Test@123", "Test@123");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyPhoneErrorVisible("Số điện thoại phải có 10 chữ số");
    }

    @Test
    public void TC02_Register_WrongFormatFullName() {
        registerPage.fillForm("T", "tc01@gmail.com", "0981116667", "testaddress", "Test@123", "Test@123");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyNameError("Họ và tên phải có ít nhất 2 ký tự");
    }

    @Test
    public void TC03_Register_MissMatchPassword() {
        registerPage.fillForm("Test User", "tc01@gmail.com", "098111", "testaddress", "Test@123", "Test@1235");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyConfirmPassErrorVisible("Mật khẩu xác nhận không khớp");
    }

    @Test
    public void TC04_Register_WrongFormatPassword() {
        registerPage.fillForm("Test User", "tc01@gmail.com", "0981110909", "testaddress", "Test", "Test");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyPassErrorVisible("Mật khẩu phải có ít nhất 6 ký tự");
    }

    @Test
    public void TC05_Register_WrongFormatAddress() {
        registerPage.fillForm("Test User", "tc01@gmail.com", "0981110909", "test", "Test@123", "Test@123");
        registerPage.submitForm(true);
        registerPage.verifyAddressErrorVisible("Địa chỉ phải có ít nhất 10 ký tự");
    }

    @Test
    public void TC06_Register_DuplicateEmail() {
        registerPage.fillForm("Test User", "hoangchan@gmail.com", "0987654321", "test", "Test@123", "Test@123");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyDuplicateError();
    }

    @Test
    public void TC07_Register_DuplicatePhone() {
        registerPage.fillForm("Test User", "tc01@gmail.com", "0987654321", "test", "Test@123", "Test@123");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyDuplicateError();
    }

    @Test
    public void TC08_Register_WrongFormatEmail() {
        registerPage.fillForm("Test User", "tc01", "098111090998", "testadress test", "Test@123", "Test@123");
        registerPage.chooseAddress();
        registerPage.submitForm(true);
        registerPage.verifyEmailErrorVisible("Email không hợp lệ");
    }

    @Test
    public void TC09_Register_MissCheckBox() {
        registerPage.fillForm("Test User", "tc01@gmail.com", "098111090998", "testadress test", "Test@123", "Test@123");
        registerPage.chooseAddress();
        registerPage.submitForm(false);
        registerPage.verifyNotCheckErrorVisible("Bạn phải đồng ý với điều khoản sử dụng");
    }

    @Test
    public void TC10_Register_EmptyAllField() {
        registerPage.fillForm("", "", "", "", "", "");
        registerPage.submitForm(false);
        registerPage.verifyPhoneErrorVisible("Số điện thoại phải có 10 chữ số");
        registerPage.verifyNotCheckErrorVisible("Bạn phải đồng ý với điều khoản sử dụng");
        registerPage.verifyEmailErrorVisible("Email không hợp lệ");
        registerPage.verifyAddressErrorVisible("Địa chỉ phải có ít nhất 10 ký tự");
        registerPage.verifyPassErrorVisible("Mật khẩu phải có ít nhất 6 ký tự");
        registerPage.verifyNameError("Họ và tên phải có ít nhất 2 ký tự");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}