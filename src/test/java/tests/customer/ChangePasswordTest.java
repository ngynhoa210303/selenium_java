package tests.customer;

import components.customer.ChangePassWordComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.customer.RegisterPage;
import utils.Config;

import java.time.Duration;

public class ChangePasswordTest {

    WebDriver driver;
    WebDriverWait wait;
    RegisterPage registerPage;
    LoginPage loginPage;
    ChangePassWordComponent changePassWordComponent;

    String email_customer = "hoanghung44@neyop.com";
    String password_customer = "Test@123";
    String new_password_customer = "Test@1234";

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(Config.BASE_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        changePassWordComponent = new ChangePassWordComponent(driver);
        driver.get(Config.BASE_URL + "/login");
        loginPage.login(email_customer, password_customer);
        loginPage.verifyLoginSuccessMessage();
        driver.get(Config.BASE_URL + "/profile");
        changePassWordComponent.openChangePassword();
    }

    @Test
    public void TC11_ChangePassword_WrongPassword() {
        changePassWordComponent.changePassword("123445", "Test@123", "Test@123");
        changePassWordComponent.submit();
        changePassWordComponent.verifyCurrentPass();
    }

    @Test
    public void TC12_ChangePassword_MissMatch() {
        changePassWordComponent.changePassword(password_customer, "Test@1234", "Test@123");
        changePassWordComponent.submit();
        changePassWordComponent.verifyMissMatchPassword();
    }

    @Test
    public void TC13_ChangePassword_Success() {
        changePassWordComponent.changePassword(password_customer, new_password_customer, new_password_customer);
        changePassWordComponent.submit();
        changePassWordComponent.verifySuccessChangePassMessage();
        loginPage.logout();
        loginPage.login(email_customer, new_password_customer);
        loginPage.verifyLoginSuccessMessage();
        driver.get(Config.BASE_URL + "/profile");
        changePassWordComponent.openChangePassword();
        changePassWordComponent.changePassword(new_password_customer, password_customer, password_customer);
        changePassWordComponent.submit();
        changePassWordComponent.verifySuccessChangePassMessage();
        loginPage.logout();
        loginPage.login(email_customer, password_customer);
        loginPage.verifyLoginSuccessMessage();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}