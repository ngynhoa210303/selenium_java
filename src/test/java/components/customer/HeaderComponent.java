package components.customer;

import components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HeaderComponent extends BaseComponent {

    WebDriver driver;
    WebDriverWait wait;

    public HeaderComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By homeLink = By.xpath("//a[contains(text(),'Trang chủ')]");
    By productLink = By.xpath("//a[contains(text(),'Sản phẩm')]");
    By orderListLink = By.xpath("//a[contains(text(),'Tra cứu đơn hàng')]");
    By cartIcon = By.xpath("//a[@class='relative']//button[1]");

    public void openHomePage() {
        click(homeLink);
    }

    public void openCartPage() {
        click(cartIcon);
    }

    public void openProductPage() {
        click(productLink);
    }

    public void openOrderPage() {
        click(orderListLink);
    }

}
