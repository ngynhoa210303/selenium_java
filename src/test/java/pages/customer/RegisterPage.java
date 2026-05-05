package pages.customer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.BasePage;
import utils.Config;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver) {
        super(driver);
        WebDriverWait wait;
    }

    String baseUrl = Config.BASE_URL;
    By registerLink = By.xpath("//a[contains(.,'Đăng ký')]");
    By submitBtn = By.xpath("//button[contains(.,'Đăng ký')]");
    By nameInput = By.xpath("//input[@placeholder='Nguyễn Văn A']");
    By emailInput = By.xpath("//input[@placeholder='your.email@example.com']");
    By phoneInput = By.xpath("//input[@placeholder='0123456789']");
    By addressInput = By.id("new-address");
    By passwordInput = By.xpath("//input[@placeholder='Nhập mật khẩu']");
    By confirmPasswordInput = By.xpath("//input[@placeholder='Nhập lại mật khẩu']");
    By checkbox = By.xpath("//button[@role='checkbox']");
    By provinceCombobox = By.xpath("//span[contains(text(),'Tỉnh')]/ancestor::button");
    By communesCombobox = By.xpath("//span[contains(text(),'Phường')]/ancestor::button");
    By firstOption = By.xpath("(//div[@role='option'])[1]");
    By nameError = By.xpath("//label[normalize-space(text())='Họ và tên']/following-sibling::p");
    By emailError = By.xpath("//label[normalize-space(text())='Email']/following-sibling::p");
    By phoneError = By.xpath("//label[normalize-space(text())='Số điện thoại']/following-sibling::p");
    By addressError = By.xpath("//div[contains(@class,'flex flex-col gap-2')]/following-sibling::p");
    By confirmPassError = By.xpath("//label[normalize-space(text())='Xác nhận mật khẩu']/following-sibling::p");
    By passError = By.xpath("//label[normalize-space(text())='Mật khẩu']/following-sibling::p");
    By notCheckError = By.xpath("//div[contains(@class,'gap-2 flex')]//p");
    By duplicateError = By.xpath("//div[contains(.,'Số điện thoại hoặc email đã tồn tại')]");
    By loadingRegister = By.xpath("//p[@class='text-wood-900 font-medium']");

    public void openRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    public void fillForm(String name, String email, String phone, String address, String password, String confirmPassWord) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameInput)).sendKeys(name);
        sendKeys(emailInput, email);
        sendKeys(phoneInput, phone);
        sendKeys(addressInput, address);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,300)");
        sendKeys(passwordInput, password);
        sendKeys(confirmPasswordInput, confirmPassWord);
    }

    public void chooseAddress() {
        click(provinceCombobox);
        click(firstOption);
        click(communesCombobox);
        click(firstOption);
    }

    public void submitForm(boolean check) {
        if (check) {
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
        }
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
    }

    public void verifyNameError(String expectedMessage) {
        verifyError(nameError, expectedMessage);
    }

    public void verifyEmailErrorVisible(String expectedMessage) {
        verifyError(emailError, expectedMessage);
    }

    public void verifyPhoneErrorVisible(String expectedMessage) {
        verifyError(phoneError, expectedMessage);
    }

    public void verifyAddressErrorVisible(String expectedMessage) {
        verifyError(addressError, expectedMessage);
    }

    public void verifyConfirmPassErrorVisible(String expectedMessage) {
        verifyError(confirmPassError, expectedMessage);
    }
    public void verifyPassErrorVisible(String expectedMessage) {
        verifyError(passError, expectedMessage);
    }
    public void verifyDuplicateError() {
        waitForInvisible(loadingRegister);
        waitForInvisible(duplicateError);
    }

    public void verifyNotCheckErrorVisible(String expectedMessage) {
        verifyError(notCheckError, expectedMessage);
    }

    public void verifyError(By locator, String expectedText) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));// đảm bảo visible
        String actualText = el.getText().trim();

        Assert.assertEquals(
                actualText,
                expectedText,
                "Sai message!\nExpected: " + expectedText + "\nActual: " + actualText
        );
    }
}
