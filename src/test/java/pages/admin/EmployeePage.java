package pages.admin;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.BasePage;

import java.time.Duration;
import java.util.List;

public class EmployeePage extends BasePage {
    WebDriver driver;
    WebDriverWait wait;

    public EmployeePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By addEmployeeBtn = By.xpath("//button[normalize-space(text())='Thêm nhân viên']");
    By phoneInput = By.name("phone");
    By fullNameInput = By.name("fullName");
    By emailInput = By.name("email");
    By employeeCodeInput = By.name("employeeCode");
    By passwordInput = By.name("password");
    By submitBtn = By.xpath("//button[contains(text(),'Tạo tài khoản')]");
    By searchInput = By.xpath("//input[@data-slot='input']");
    By roleDropdown = By.xpath("(//button[@role='combobox'])[1]");
    By statusDropdown = By.xpath("(//button[@role='combobox'])[2]");
    //    Create Employee
    private By phoneError = By.xpath("//p[contains(text(),'Số điện thoại')]");
    private By emailError = By.xpath("//p[contains(text(),'Email')]");
    private By nameError = By.xpath("//p[contains(text(),'Họ tên')]");
    private By passwordError = By.xpath("//p[contains(text(),'Mật khẩu')]");
    private By codeError = By.xpath("//p[contains(text(),'Mã nhân viên')]");

    private By addEmployeeModal = By.xpath("//div[@role='dialog']");
    private By successToast = By.xpath("//div[contains(.,'Tạo tài khoản nhân viên thành công')]");
    private By tableRows = By.xpath("//table//tbody//tr");

    private By roleCombobox = By.xpath("//label[@data-slot='form-label']/following-sibling::button[1]");

    private By roleOption(int index) {
        return By.xpath("(//div[@role='option'])[" + index + "]");
    }

    public void openAddEmployeeForm() {
        click(addEmployeeBtn);
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
    }

    public void fillEmployeeForm(String phone, String name, String email, String code, String pass) {
        driver.findElement(phoneInput).clear();
        driver.findElement(phoneInput).sendKeys(phone);
        slow(1000);
        driver.findElement(fullNameInput).clear();
        driver.findElement(fullNameInput).sendKeys(name);
        slow(1000);
        driver.findElement(emailInput).clear();
        driver.findElement(emailInput).sendKeys(email);
        slow(1000);
        driver.findElement(employeeCodeInput).clear();
        driver.findElement(employeeCodeInput).sendKeys(code);
        slow(1000);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(pass);
    }

    public void fillEmployeeEditForm(String phone, String name, String code) {
        sendKeys(phoneInput,phone);
        slow(1000);
        sendKeys(fullNameInput,name);
        slow(1000);
        sendKeys(employeeCodeInput,code);
    }

    public void clearInput(By locator) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        el.sendKeys(Keys.DELETE);
        wait.until(driver -> el.getAttribute("value").isEmpty());
    }

    public void resetEditForm() {
        clearInput(phoneInput);
        clearInput(fullNameInput);
        clearInput(employeeCodeInput);
    }

    public String[] getData() {
        String dataPhone = driver.findElement(phoneInput).getAttribute("value");
        String dataName = driver.findElement(fullNameInput).getAttribute("value");
        String dataCode = driver.findElement(employeeCodeInput).getAttribute("value");
        return new String[]{dataPhone, dataName, dataCode};
    }

    public void selectRole(String roleName) {
        wait.until(ExpectedConditions.elementToBeClickable(roleCombobox)).click();
        By option = By.xpath("//div[@role='option']//span[text()='" + roleName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public String getSelectedRole(By roleCombobox) {
        return driver.findElement(roleCombobox).getText().trim();
    }

    public void submitForm() {
        WebElement btnSubmit = driver.findElement(By.xpath("//button[@type='submit']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnSubmit);
    }

    public void cancelForm() {
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(text(),'Hủy')]"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btnSubmit);
    }

    public void search(String keyword) {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        BasePage.slow(1000);
    }

    public void selectSearchRole(By option) {
        wait.until(ExpectedConditions.elementToBeClickable(roleDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectStatus(By option) {
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(option)).click();
    }

    public void selectRoleByIndex(int index) {
        wait.until(ExpectedConditions.elementToBeClickable(roleCombobox)).click();
        wait.until(ExpectedConditions.elementToBeClickable(roleOption(index))).click();
    }

    public void waitForCreateSuccess() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(addEmployeeModal));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successToast));
    }

    public boolean isPhoneErrorVisible() {
        return isVisible(phoneError);
    }

    public boolean isEmailErrorVisible() {
        return isVisible(emailError);
    }

    public boolean isCodeErrorVisible() {
        return isVisible(codeError);
    }

    public String getPasswordErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }

    public String getNameErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).getText();
    }

    public boolean isVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmployeeExist() {
        List<WebElement> rows = driver.findElements(tableRows);
        return !rows.isEmpty();
    }

    public void disableHtml5Validation() {
        ((JavascriptExecutor) driver)
                .executeScript("document.querySelector('form').noValidate = true;");
    }

    public String getPhoneErrorText() {
        return getText(phoneError);
    }

    public boolean isAllRequiredErrorsVisible() {
        return isVisible(phoneError)
                && isVisible(emailError)
                && isVisible(nameError)
                && isVisible(passwordError);
    }
}