package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.time.Duration;

public class LoginPage extends BasePage{

    WebDriver driver;
    WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By emailInput = By.id("email");
    By passwordInput = By.id("password");
    By loginButton = By.xpath("//button[contains(.,'Đăng nhập')]");
    By avataButton = By.xpath("(//div[@class='relative group']//button)[1]");
    By logoutButton = By.xpath("//button[contains(text(),'Đăng xuất')]");
    By loginToastMessage = By.xpath("//div[contains(text(),'Đăng nhập thành công')]");
    By loginErrorToastMessage = By.xpath("//div[contains(text(),'Tài khoản hoặc mật khẩu không chính xác')]");
    By loadingLogin = By.xpath("//p[@class='text-wood-900 font-medium']");

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).sendKeys(email);
        slow(1000);
        driver.findElement(passwordInput).sendKeys(password);
        slow(1000);
        driver.findElement(loginButton).click();
        slow(500);

    }
    public void logout() {
        Actions actions = new Actions(driver);
        actions.moveToElement(wait.until(
                ExpectedConditions.visibilityOfElementLocated(avataButton)
        )).perform();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }
    public void verifyLoginSuccessMessage() {
        waitForInvisible(loadingLogin);
    }
    public void verifyLoginErrorMessage() {
        waitForVisible(loginErrorToastMessage);
        waitForInvisible(loginErrorToastMessage);
    }
}
