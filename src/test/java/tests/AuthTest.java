package tests;

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
import utils.Config;

import java.time.Duration;

public class AuthTest {

    WebDriver driver;
    LoginPage loginPage;
    MenuBarComponent menuBarComponent;
    WebDriverWait wait;
    String baseUrl = Config.BASE_URL;

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl + "/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
        menuBarComponent = new MenuBarComponent(driver);
    }
    @Test
    public void TC14_Login_WrongEmail() {
        loginPage.login("testhananrun@gmail.com", "Test@123");
        loginPage.verifyLoginErrorMessage();
    }
    @Test
    public void TC15_Login_WrongPassword() {
        loginPage.login("hoanghung44@neyop.com", "Test@12356");
        loginPage.verifyLoginErrorMessage();
    }
    @Test
    public void TC16_LoginCustomer() {
        loginPage.login("hoanghung44@neyop.com", "Test@123");
        loginPage.verifyLoginSuccessMessage();
    }

    @Test
    public void TC17_LoginAdmin() {
        loginPage.login("hoanghuyen4@neyop.com", "Test@123");
        loginPage.verifyLoginSuccessMessage();
        wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Không chuyển sang dashboard!");
    }

    @Test
    public void TC18_LoginEmployee() {
        loginPage.login("maihien@neyop.com", "Test@123");
        loginPage.verifyLoginSuccessMessage();
    }

    @Test
    public void TC19_LoginDisabledAccount() {
        loginPage.login("namtuan45@gmail.com", "Test@123");
        loginPage.verifyLoginErrorMessage();
    }

    @Test
    public void TC20_LogoutCustomer() {
        loginPage.login("hoanghung44@neyop.com", "Test@123");
        loginPage.verifyLoginSuccessMessage();
        loginPage.logout();
        wait.until(ExpectedConditions.urlContains("login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Logout không thành công!");
    }

    @Test
    public void TC21_LogoutAdmin() {
        loginPage.login("hoanghuyen4@neyop.com", "Test@123");
        wait.until(ExpectedConditions.urlContains("dashboard"));
        driver.get(baseUrl);
        loginPage.logout();
        wait.until(ExpectedConditions.urlContains("login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Logout admin thất bại!");
    }

    @Test
    public void TC22_LogoutEmployee() {
        loginPage.login("maihien@neyop.com", "Test@123");
        loginPage.verifyLoginSuccessMessage();
        wait.until(ExpectedConditions.urlContains("dashboard"));
        driver.get(baseUrl);
        loginPage.logout();
        wait.until(ExpectedConditions.urlContains("login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "Logout employee thất bại!");
    }

    @Test
    public void TC23_Logout_ByMenuBar() {
        loginPage.login("maihien@neyop.com", "Test@123");
        wait.until(ExpectedConditions.urlContains("dashboard"));

        menuBarComponent.logout();

        wait.until(ExpectedConditions.urlContains("login"));
        Assert.assertTrue(driver.getCurrentUrl().contains("login"), "❌ Logout qua menu thất bại!");
    }


    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}