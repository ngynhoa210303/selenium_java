package pages.customer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class OrderTrackingPage extends BasePage {

    public OrderTrackingPage(WebDriver driver) {
        super(driver);
    }

    By orderTrackingInputSearch = By.xpath("//input[@id='orderNumber']");
    By searchButton = By.xpath("//button[contains(text(),'Tìm kiếm')]");

    public void searchOrder(String text) {
        sendKeys(orderTrackingInputSearch, text);
        click(searchButton);
    }

    public String[] getOrderDetailAsArray() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'grid grid-cols-1')]")
        ));

        String name = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Tên')]]"))
                .getText().replace("Tên:", "").trim();

        String phone = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'SĐT')]]"))
                .getText().replace("SĐT:", "").trim();

        String address = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Địa chỉ')]]"))
                .getText().replace("Địa chỉ:", "").trim();

        String orderDate = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Ngày đặt')]]"))
                .getText().replace("Ngày đặt:", "").trim();

        String channel = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Kênh')]]"))
                .getText().replace("Kênh:", "").trim();

        String product = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Sản phẩm')]]"))
                .getText().replace("Sản phẩm:", "").trim();

        String total = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Tổng tiền')]]"))
                .getText().replace("Tổng tiền:", "").trim();

        String paid = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Đã thanh toán')]]"))
                .getText().replace("Đã thanh toán:", "").trim();

        String remain = container.findElement(By.xpath(".//p[span[contains(normalize-space(),'Còn lại')]]"))
                .getText().replace("Còn lại:", "").trim();

        return new String[]{
                name, phone, address,
                orderDate, channel, product,
                total, paid, remain
        };
    }
}
