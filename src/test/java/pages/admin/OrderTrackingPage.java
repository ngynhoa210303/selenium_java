package pages.admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;


public class OrderTrackingPage extends BasePage {

    public OrderTrackingPage(WebDriver driver) {
        super(driver);
    }

    By orderTrackingInputSearch = By.xpath("//div[@class='relative']//input[1]");
    By orderListEmpty = By.xpath("//div[contains(text(),'Chưa có đơn hàng nào')]");

    public void searchOrder(String length) {
        sendKeys(orderTrackingInputSearch, length);
    }

    public String[] getDataInTheOrderList(By rowLocator) {
        WebElement row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowLocator));

        String orderCode = row.findElement(By.xpath(".//td[1]//div[contains(@class,'font-medium')]")).getText();
        String customerName = row.findElement(By.xpath(".//td[2]//div[contains(@class,'font-medium')]")).getText();
        String onlineStatus = row.findElement(By.xpath(".//td[3]//span")).getText();
        String orderTime = row.findElement(By.xpath(".//td[4]")).getText();
        String paymentStatus = row.findElement(By.xpath(".//td[5]//span")).getText();
        String totalAmount = row.findElement(By.xpath(".//td[6]")).getText();

        return new String[]{
                orderCode,
                customerName,
                onlineStatus,
                orderTime,
                paymentStatus,
                totalAmount
        };
    }

    public void verifyEmptyList() {
        waitForVisible(orderListEmpty);
    }
}
