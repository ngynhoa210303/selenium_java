package components.customer;

import components.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ChangePassWordComponent extends BaseComponent {
    public ChangePassWordComponent(WebDriver driver) {
        super(driver);
    }

    By changePassOpenComponent = By.xpath("//button[contains(text(),'Đổi mật khẩu')]");
    By currentPassword = By.xpath("//input[@name='currentPassword']");
    By newPassword = By.xpath("//input[@name='newPassword']");
    By confirmPassword = By.xpath("//input[@name='confirmPassword']");
    By submitButton = By.xpath("//button[normalize-space(text())='Đổi mật khẩu']");
    By cancelButton = By.xpath("//button[@data-slot='button']/following-sibling::button[1]");
    By errorMissMatch = By.xpath("//div[contains(.,'Mật khẩu xác nhận không khớp')]");
    By errorCurrentPassNotMatch = By.xpath("//div[contains(.,'Mật khẩu hiện tại không đúng hoặc có lỗi xảy ra')]");
    By successChangePassMessage = By.xpath("//div[contains(.,'Đổi mật khẩu thành công!')]");

    public void openChangePassword() {
        click(changePassOpenComponent);
    }

    public void changePassword(String current, String newPass, String confirm) {
        sendKeys(currentPassword, current);
        sendKeys(newPassword, newPass);
        sendKeys(confirmPassword, confirm);
    }

    public void submit() {
        click(submitButton);
    }
    public void verifyCurrentPass() {
        waitForVisible(errorCurrentPassNotMatch);
    }
    public void verifyMissMatchPassword() {
        waitForVisible(errorMissMatch);
    }
    public void verifySuccessChangePassMessage() {
        waitForVisible(successChangePassMessage);
    }
}
