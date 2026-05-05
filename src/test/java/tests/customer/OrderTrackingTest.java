package tests.customer;

import components.admin.MenuBarComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.customer.OrderTrackingPage;
import utils.Config;

import java.time.Duration;

public class OrderTrackingTest {
    WebDriver driver;
    OrderTrackingPage orderTrackingPage;
    MenuBarComponent menuComponent;
    WebDriverWait wait;

    String baseUrl = Config.BASE_URL;

    @BeforeMethod
    public void setUp() {
        String path = System.getProperty("user.dir") + "\\drivers\\msedgedriver.exe";
        System.setProperty("webdriver.edge.driver", path);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(baseUrl + "/tracking");
        wait.until(ExpectedConditions.urlContains("/tracking"));
        orderTrackingPage = new OrderTrackingPage(driver);
    }

    @Test
    public void TC70_Guess_SearchByOrderCodeExact() {
        orderTrackingPage.searchOrder("ORD-2026419-0115");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'grid grid-cols-1')]")
        ));
        String[] expected = {
                "Hoang Van Hung",
                "0987654321",
                "78 SD, Phường Liên Hòa, Quảng Ninh",
                "19 tháng 4, 2026",
                "Trực tuyến",
                "1 mặt hàng",
                "1.030.000 ₫",
                "0 ₫",
                "1.030.000 ₫"
        };
        Assert.assertEquals(orderTrackingPage.getOrderDetailAsArray(), expected);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
