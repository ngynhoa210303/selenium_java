package tests.customer;

import components.customer.HeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.admin.EmployeePage;
import pages.customer.CartPage;
import pages.customer.ProductPage;
import utils.Config;

import java.time.Duration;

public class CartCustomerTest {

    WebDriver driver;
    WebDriverWait wait;
    CartPage cartPage;
    HeaderComponent headerComponent;
    String baseurl = Config.BASE_URL;

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(baseurl + "/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        cartPage = new CartPage(driver);
        headerComponent = new HeaderComponent(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("hoanghung44@neyop.com", "Test@123");
        driver.get(baseurl + "/products");
    }

    @Test
    public void TC55_Customer_AddSingleProductToCart() {
        ProductPage productPage = new ProductPage(driver);
        productPage.openFirstProduct();
        productPage.selectOption();
        productPage.addToCart();
        productPage.waitCartCount("1");
        headerComponent.openCartPage();
        cartPage.clickDeleteProduct();
        cartPage.waitForEmptyCart();
        driver.navigate().refresh();
        cartPage.waitForEmptyCart();
    }

    @Test
    public void TC56_Customer_AddSameProductMultipleTimes() {
        ProductPage productPage = new ProductPage(driver);
        productPage.openFirstProduct();
        productPage.selectOption();
        productPage.addToCart();
        productPage.waitCartCount("1");
        productPage.addToCart();
        productPage.waitCartCount("2");
        headerComponent.openCartPage();
        cartPage.clickDeleteProduct();
        cartPage.waitForEmptyCart();
        driver.navigate().refresh();
        cartPage.waitForEmptyCart();
    }

    @Test
    public void TC57_Customer_AddDifferentProductsToCart() {
        ProductPage productPage = new ProductPage(driver);
        productPage.addFirstProductToCart();
        productPage.waitCartCount("1");
        productPage.addSecondProductToCart(baseurl);
        productPage.waitCartCount("2");
        headerComponent.openCartPage();
        cartPage.waitItemQuantity(1, "1");
        cartPage.waitItemQuantity(2, "1");
        cartPage.deleteAllProducts();
        driver.navigate().refresh();
        cartPage.waitForEmptyCart();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}