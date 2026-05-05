package tests.customer;

import components.customer.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.customer.CartPage;
import pages.customer.ProductPage;
import utils.Config;

import java.time.Duration;

public class CartGuessTest {

    WebDriver driver;
    WebDriverWait wait;
    CartPage cartPage;
    HeaderComponent headerComponent;
    String baseUrl = Config.BASE_URL;

    ProductPage productPage;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.edge.driver",
                System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe");

        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl + "/products");
        cartPage = new CartPage(driver);
        headerComponent = new HeaderComponent(driver);
        productPage = new ProductPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void TC24_Guest_AddSingleProductToCart() {
        productPage.openFirstProduct();
        productPage.selectOption();
        productPage.addToCart();
        productPage.waitCartBadge("1");
        cartPage.openCart(baseUrl);
        cartPage.waitItemQuantity(1, "1");
    }

    @Test
    public void TC25_Guest_AddSameProductMultipleTimes() {
        productPage.openFirstProduct();
        productPage.selectOption();
        productPage.addToCart();
        productPage.waitCartBadge("1");
        productPage.addToCart();
        productPage.waitCartBadge("2");
        cartPage.openCart(baseUrl);
        cartPage.waitItemQuantity(1, "2");
    }

    @Test
    public void TC26_Guest_AddDifferentProductsToCart() {
        productPage.openFirstProduct();
        productPage.selectOption();
        productPage.addToCart();
        productPage.waitCartBadge("1");
        driver.get(baseUrl + "/products");
        productPage.openSecondProduct();
        productPage.selectOption();
        productPage.addToCart();
        productPage.waitCartBadge("2");

        cartPage.openCart(baseUrl);
        cartPage.waitItemQuantity(1, "1");
        cartPage.waitItemQuantity(2, "1");
    }

    @Test
    public void TC27_Guest_RequestOrder() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.waitErrorRequestInvisible();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}