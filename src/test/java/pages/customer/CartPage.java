package pages.customer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;
    By deleteOneProductBtn;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.deleteOneProductBtn = By.xpath("//div[@class='flex-1 space-y-1']/following-sibling::button[1]");
    }
    By emptyCartMessage = By.xpath("//h1[text()='Giỏ hàng trống']/following-sibling::p");
    public void deleteAllProducts() {
        clickFirstButtonMultipleTimes();
    }

    public boolean isCartEmpty() {
        return isDisplayed(emptyCartMessage);
    }

    public void waitForEmptyCart() {
        waitForVisible(emptyCartMessage);
    }
    public void clickDeleteProduct() {
        click(deleteOneProductBtn);
    }

    public void clickFirstButtonMultipleTimes() {
        By buttons = By.xpath("//div[@class='flex-1 space-y-1']/following-sibling::button[1]");
        while (true) {
            List<WebElement> list = driver.findElements(buttons);
            if (list.isEmpty()) break;
            WebElement first = list.get(0);
            first.click();
            wait.until(ExpectedConditions.stalenessOf(first));
        }
    }
    public void waitItemQuantity(int index, String expected) {
        By item = By.xpath("(//button[@data-slot='button']/following-sibling::span)[" + index + "]");
        wait.until(ExpectedConditions.textToBe(item, expected));
    }
    public void openCart(String baseUrl) {
        driver.get(baseUrl + "/cart");
    }
}
