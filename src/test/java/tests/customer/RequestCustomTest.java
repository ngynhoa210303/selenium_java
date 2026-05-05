package tests.customer;

import components.customer.HeaderComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.customer.CartPage;
import pages.customer.ProductPage;
import utils.Config;

import java.time.Duration;

public class RequestCustomTest {
    WebDriver driver;
    WebDriverWait wait;
    CartPage cartPage;
    HeaderComponent headerComponent;
    ProductPage productPage;
    String baseurl= Config.BASE_URL;
    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(baseurl+"/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        cartPage= new CartPage(driver);
        productPage = new ProductPage(driver);
        headerComponent= new HeaderComponent(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("hoanghung44@neyop.com","Test@123");
    }
    @Test
    public void TC58_Customer_RequestOrder_EmptyForm() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.clickSendRequest();
        productPage.verifyMissError();
    }
    @Test
    public void TC59_Customer_RequestOrder_LengthInvalid() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("abc", "50", "30", "test");
        productPage.clickSendRequest();
        productPage.verifyMissError();

    }
    @Test
    public void TC60_Customer_RequestOrder_LengthLessThanOrEqualZero() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("0", "50", "30", "test");
        productPage.clickSendRequest();
        productPage.verifySizeError();
    }

    @Test
    public void TC61_Customer_RequestOrder_WidthInvalid() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("100", "abc", "30", "test");
        productPage.clickSendRequest();
        productPage.verifyMissError();
    }
    @Test
    public void TC62_Customer_RequestOrder_WidthLessThanOrEqualZero() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("100", "0", "30", "test");
        productPage.clickSendRequest();
        productPage.verifySizeError();
    }

    @Test
    public void TC63_Customer_RequestOrder_HeightInvalid() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("100", "50", "abc", "test");
        productPage.clickSendRequest();
        productPage.verifyMissError();
    }

    @Test
    public void TC64_Customer_RequestOrder_HeightLessThanOrEqualZero() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("100", "50", "-1", "test");
        productPage.clickSendRequest();
        productPage.verifySizeError();
    }
    @Test
    public void TC65_Customer_RequestOrder_NotChooseMaterial() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("100", "50", "70", "test");
        productPage.clickSendRequest();
        productPage.verifyMaterialError();
    }
    @Test
    public void TC66_Customer_RequestOrder_Success() {
        productPage.openFirstProduct();
        productPage.clickRequestOrder();
        productPage.fillCustomSize("100", "50", "70", "test");
        productPage.selectOptionByText("Đá Nhân Tạo Cao Cấp");
        productPage.clickSendRequest();
        productPage.verifySuccessSendRequestMessage();
        driver.get(baseurl + "/custom-request");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
