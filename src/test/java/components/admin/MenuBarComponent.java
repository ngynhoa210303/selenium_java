package components.admin;

import components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class MenuBarComponent extends BaseComponent {

    WebDriver driver;
    WebDriverWait wait;

    public MenuBarComponent(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By productCollapse = By.xpath("(//ul[@class='space-y-1 px-3']//button)[1]");
    By productListMenuBtn = By.xpath("//a[contains(text(),'Danh sách sản phẩm')]");
    By materialMenuBtn = By.xpath("//a[@href='/dashboard/admin/materials']");
    By orderMenuBtn = By.xpath("//a[@href='/dashboard/admin/orders']");
    By sizeMenuBtn = By.xpath("//a[@href='/dashboard/admin/sizes']");
    By customerCollapse = By.xpath("(//ul[@class='space-y-1 px-3']//button)[2]");
    By customerListMenuBtn = By.xpath("//a[normalize-space()='Danh sách khách hàng']");
    By userListMenuBtn = By.xpath("//a[contains(text(),'Danh sách người dùng')]");
    By logoutBtn = By.xpath("//button[contains(text(),'Đăng xuất')]");
    By myProfileBtn = By.xpath("//button[contains(text(),'Hồ sơ của tôi')]");

    public void openProductMenu() {
        click(productCollapse);
    }

    public void goToProductList() {
        click(productListMenuBtn);
    }

    public void goToMaterial() {
        click(materialMenuBtn);
    }

    public void goToSize() {
        click(sizeMenuBtn);
    }

    public void openCustomerMenu() {
        click(customerCollapse);
    }

    public void goToCustomerList() {
        click(customerListMenuBtn);
    }

    public void goToUserList() {
        click(userListMenuBtn);
    }
    public void goToOrderList() {
        click(orderMenuBtn);
    }

    public void logout() {
        click(logoutBtn);
    }
    public void goToProfilePage() {
        click(myProfileBtn);
    }
}
