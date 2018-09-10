//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myprojects.automation.assignment4;

import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import myprojects.automation.assignment4.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private GeneralActions builder;

    public GeneralActions(WebDriver driver, GeneralActions builder) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30L);
        this.builder = builder;
    }

    public void login(String login, String password) {
        CustomReporter.log("Login as user - " + login);
        this.driver.navigate().to(Properties.getBaseAdminUrl());
        this.driver.findElement(By.id("email")).sendKeys(new CharSequence[]{login});
        this.driver.findElement(By.id("passwd")).sendKeys(new CharSequence[]{password});
        this.driver.findElement(By.name("submitLogin")).click();
        this.wait.until(ExpectedConditions.presenceOfElementLocated(By.id("main")));
    }

    public void createProduct(ProductData product) {
        CustomReporter.log(String.format("Product %s, Price: %s, QTY: %s, Weight: %s", product.getName(), product.getPrice(), product.getQty(), product.getWeight()));
        WebElement element = this.waitForContentLoad(By.id("form_step1_name_1"));
        element.sendKeys(new CharSequence[]{product.getName()});
        element = this.waitForContentLoad(By.id("form_step6_reference"));
        element.sendKeys(new CharSequence[]{product.getKey()});
        element = this.waitForContentLoad(By.id("form_step1_qty_0_shortcut"));
        element.sendKeys(new CharSequence[]{Integer.toString(product.getQty())});
        element.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}), Integer.toString(product.getQty())});
        element = this.waitForContentLoad(By.id("form_step1_price_shortcut"));
        this.scrollTo(element);
        element.sendKeys(new CharSequence[]{Keys.chord(new CharSequence[]{Keys.CONTROL, "a"}), product.getPrice()});
        element = this.driver.findElement(By.id("add_feature_button"));
        this.scrollTo(element);
        element.click();
        Select slElement = new Select(this.waitForContentLoad(By.id("form_step1_features_0_feature")));
        slElement.selectByValue("4");
        element = this.waitForContentLoad(By.id("form_step1_features_0_custom_value_1"));
        element.sendKeys(new CharSequence[]{product.getWeight()});
        element = this.driver.findElement(By.className("switch-input"));
        element.click();
        this.wait.until(ExpectedConditions.textToBe(By.className("growl-message"), "Настройки обновлены."));
        this.driver.findElement(By.cssSelector(".growl-close")).click();
        element = this.waitForClickable(By.id("submit"));
        element.click();
        this.wait.until(ExpectedConditions.textToBe(By.className("growl-message"), "Настройки обновлены."));
        this.driver.findElement(By.cssSelector(".growl-close")).click();
        element = this.waitForClickable(By.id("product_form_save_go_to_catalog_btn"));
        element.click();
        this.waitForContentLoad(By.id("page-header-desc-configuration-add"));
    }

    public void checkProduct(ProductData product) {
        this.driver.findElement(By.cssSelector(".all-product-link")).click();
        WebElement element = this.waitForContentLoad(By.linkText(product.getName()));
        this.scrollTo(element);
        element.click();
        element = this.waitForContentLoad(By.cssSelector(".breadcrumb li:last-child span[itemprop='name']"));
        CustomReporter.log("check the breadcrumb name - " + element.getText());
        Assert.assertEquals(element.getText(), product.getName());
        element = this.waitForContentLoad(By.cssSelector("h1[itemprop='name']"));
        CustomReporter.log("check the name - " + element.getText());
        Assert.assertEquals(element.getText(), product.getName().toUpperCase());
        element = this.waitForContentLoad(By.cssSelector(".current-price span"));
        CustomReporter.log("check the price - " + element.getAttribute("content"));
        String price = product.getPrice();
        if (!",".contains(element.getAttribute("content"))) {
            price = price.replace(',', '.');
        }

        Assert.assertEquals(element.getAttribute("content"), price);
        element = this.waitForContentLoad(By.cssSelector(".product-quantities span"));
        CustomReporter.log("check the QTY - " + element.getText());
        Assert.assertEquals(element.getText(), product.getQty() + " Товары");
        element = this.waitForContentLoad(By.cssSelector("#product-details > section > dl > dt"));
        CustomReporter.log("check the weight label - " + element.getText());
        Assert.assertEquals(element.getText(), "Weight");
        element = this.waitForContentLoad(By.cssSelector("#product-details > section > dl > dd"));
        CustomReporter.log("check the weight - " + element.getText());
        Assert.assertEquals(element.getText(), product.getWeight());
    }

    public void open() {
        this.driver.navigate().to(Properties.getBaseUrl());
        this.waitForContentLoad(By.id("main"));
    }

    public void goToCatalogGoods() {
        this.driver.findElement(By.id("subtab-AdminCatalog")).click();
    }

    public void goToCreateNewProduct() {
        this.goToCatalogGoods();
        this.waitForContentLoad(By.id("page-header-desc-configuration-add")).click();
    }

    public WebElement waitForContentLoad(By locator) {
        return (WebElement)this.wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement waitForClickable(By locator) {
        return (WebElement)this.wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor)this.driver).executeScript("arguments[0].scrollIntoView(true);", new Object[]{element});
    }
}
