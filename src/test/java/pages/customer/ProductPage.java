package pages.customer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;

public class ProductPage extends BasePage {

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    private By firstProductDetailBtn = By.xpath("(//button[contains(.,'Xem chi tiết')])[1]");
    private By secondProductDetailBtn = By.xpath("(//button[contains(.,'Xem chi tiết')])[2]");
    private By productOptionBtn = By.xpath("(//button[@aria-checked='false'])[1]");
    private By addToCartButton = By.xpath("//button[contains(.,'Thêm vào giỏ')]");
    private By addSuccessToast = By.xpath("//div[contains(.,'Đã thêm sản phẩm vào giỏ hàng')]");
    private By cartBadgeCount = By.xpath("//span[@data-slot='badge']");
    private By cartBadge = By.xpath("//span[@data-slot='badge']");
    private By requestSendBtn = By.xpath("//button[contains(text(),'Đặt làm theo yêu cầu')]");
    private By lengthCustomInput = By.xpath("//input[@id='custom-length']");
    private By widthCustomInput = By.xpath("//input[@id='custom-width']");
    private By heightCustomInput = By.xpath("//input[@id='custom-height']");
    private By specialRQInput = By.xpath("//textarea[@id='special-requirements']");
    private By errorSendRequest = By.xpath("//div[contains(text(),'Vui lòng đăng nhập để đặt hàng theo yêu cầu')]");
    private By errorSize = By.xpath("//div[contains(text(),'Kích thước phải lớn hơn 0')]");
    private By errorMiss = By.xpath("//div[contains(text(),'Vui lòng nhập đầy đủ kích thước')]");
    private By submitRequest = By.xpath("//button[contains(text(),'Gửi yêu cầu')]");
    private By materialToastMessage = By.xpath("//div[contains(text(),'materialId must be a positive number')]");
    private By materialDropdown = By.xpath("//button[@role='combobox']");
    private By successSendRequestMessage = By.xpath("//div[contains(text(),'Đã gửi yêu cầu đặt hàng thành công! Chúng tôi sẽ l')]");
    By productName = By.xpath("//tbody/tr[1]//td[2]//a//div[1]");
    By productMaterial = By.xpath("//tbody/tr[1]//td[2]//a//div[2]");

    public void openFirstProduct() {
        click(firstProductDetailBtn);
    }

    public void openSecondProduct() {
        click(secondProductDetailBtn);
    }

    public void selectOption() {
        click(productOptionBtn);
    }

    public void addToCart() {
        click(addToCartButton);
        waitForInvisible(addSuccessToast);
    }

    public void waitCartCount(String expected) {
        wait.until(ExpectedConditions.textToBe(cartBadgeCount, expected));
    }

    public void addFirstProductToCart() {
        openFirstProduct();
        selectOption();
        addToCart();
    }

    public void addSecondProductToCart(String baseUrl) {
        driver.get(baseUrl + "/products");
        openSecondProduct();
        selectOption();
        addToCart();
    }

    public void waitCartBadge(String expected) {
        wait.until(ExpectedConditions.textToBe(cartBadge, expected));
    }

    public void clickRequestOrder() {
        click(requestSendBtn);
    }

    public void clickSendRequest() {
        click(submitRequest);
    }

    public void waitErrorRequestInvisible() {
        waitForInvisible(errorSendRequest);
    }

    public void fillCustomSize(String length, String width, String height, String note) {
        sendKeys(lengthCustomInput, length);
        sendKeys(widthCustomInput, width);
        sendKeys(heightCustomInput, height);
        sendKeys(specialRQInput, note);
    }

    public void verifySizeError() {
        waitForVisible(errorSize);
    }

    public void verifyMaterialError() {
        waitForVisible(materialToastMessage);
    }

    public void verifyMissError() {
        waitForVisible(errorMiss);
    }

    public void verifySuccessSendRequestMessage() {
        waitForVisible(successSendRequestMessage);
        waitForInvisible(successSendRequestMessage);
    }

    public void selectOptionByText(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(materialDropdown)).click();
        By optionByText = By.xpath("//div[@role='option']//span[text()='" + text + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionByText)).click();
    }

    public String[] getProductData() {
        String actualProductName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(productName)
        ).getText().trim();
        String actualMaterial = driver.findElement(productMaterial).getText().trim();
        return new String[]{actualProductName, actualMaterial};
    }

}