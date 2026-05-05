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
import pages.LoginPage;
import pages.admin.OrderTrackingPage;
import utils.Config;

import java.time.Duration;
import java.util.List;

public class OrderTrackingTest {
    WebDriver driver;
    LoginPage loginPage;
    OrderTrackingPage orderTrackingPage;
    MenuBarComponent menuComponent;
    WebDriverWait wait;

    String baseUrl = Config.BASE_URL;
    By tableRows = By.xpath("//table//tbody//tr");

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
        menuComponent.goToOrderList();
        orderTrackingPage = new OrderTrackingPage(driver);
    }

    @Test
    public void TC67_Admin_SearchOrderByOrderCodeExact() {
        orderTrackingPage.searchOrder("ORD-2026419-0115");
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//tr[1]//td[1]"),
                "ORD-2026419-0115"
        ));
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 1));
        String expectedOrderCode = "ORD-2026419-0115";
        String expectedCustomerName = "Hoang Van Hung";
        String expectedOnlineStatus = "Trực tuyến";
        String expectedOrderTime = "13:00 19/04/2026";
        String expectedPaymentStatus = "Đang chờ thanh toán";
        String expectedTotalAmount = "1.030.000 ₫";
        String actualOrderCode = orderTrackingPage.getDataInTheOrderList(tableRows)[0];
        String actualCustomerName = orderTrackingPage.getDataInTheOrderList(tableRows)[1];
        String actualOnlineStatus = orderTrackingPage.getDataInTheOrderList(tableRows)[2];
        String actualOrderTime = orderTrackingPage.getDataInTheOrderList(tableRows)[3];
        String actualPaymentStatus = orderTrackingPage.getDataInTheOrderList(tableRows)[4];
        String actualTotalAmount = orderTrackingPage.getDataInTheOrderList(tableRows)[5];
        Assert.assertEquals(actualOrderCode, expectedOrderCode, "Sai order code");
        Assert.assertEquals(actualCustomerName, expectedCustomerName, "Sai tên khách hàng");
        Assert.assertEquals(actualOnlineStatus, expectedOnlineStatus, "Sai trạng thái online");
        Assert.assertEquals(actualOrderTime, expectedOrderTime, "Sai thời gian order");
        Assert.assertEquals(actualPaymentStatus, expectedPaymentStatus, "Sai trạng thái thanh toán");
        Assert.assertEquals(actualTotalAmount, expectedTotalAmount, "Sai tổng tiền");
    }

    @Test
    public void TC68_Admin_SearchOrderByFullNameExact() {
        String customerName = "Hoang Van Hung";
        orderTrackingPage.searchOrder(customerName);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tableRows, 0));
        List<WebElement> listName = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(".//td[2]//div[contains(@class,'font-medium')]"))
        );
        boolean onlySearchName = listName.stream()
                .allMatch(e -> e.getText().trim().equals(customerName));
        Assert.assertTrue(onlySearchName, "Danh sách chứa tên khác tên cần tìm");
    }
    @Test
    public void TC69_Admin_SearchNotFound() {
        String customerName = "abcxyztest";
        orderTrackingPage.searchOrder(customerName);
        wait.until(ExpectedConditions.numberOfElementsToBe(tableRows, 0));
        orderTrackingPage.verifyEmptyList();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
