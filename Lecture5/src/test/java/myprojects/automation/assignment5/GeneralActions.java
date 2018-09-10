package myprojects.automation.assignment5;


import myprojects.automation.assignment5.model.BuyerData;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.DataConverter;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private By productLink = By.cssSelector("h1[class = 'h3 product-title']");
    private By allProductLink = By.cssSelector("a[class = 'all-product-link pull-xs-left pull-md-right h4']");

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openRandomProduct()  {
        //implement logic to open random product before purchase
        driver.findElement(allProductLink).click();
        List<WebElement> allProduct = driver.findElements(productLink);
        allProduct.get(new Random().nextInt(allProduct.size())).click();
        //throw new UnsupportedOperationException();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        // TODO extract data from opened page

        String name;
        int qty;
        float price;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href = '#product-details']")));
        name = driver.findElement(By.cssSelector("h1[itemprop = 'name']")).getText();
        price = DataConverter.parsePriceValue(driver.findElement(By.cssSelector("div.current-price span[itemprop = 'price']")).getText());
        driver.findElement(By.cssSelector("a[href = '#product-details']")).click();
        By inStock = By.cssSelector(".product-quantities > span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(inStock));
        qty = DataConverter.parseStockValue(driver.findElement(inStock).getText());


        return new ProductData(name, qty, price);
       // throw new UnsupportedOperationException();
    }

    public void addProductToCart() {
        By placeOrderLink = By.cssSelector("a[class = 'btn btn-primary']");
        By addToCartLink = By.cssSelector("div.add button[data-button-action = 'add-to-cart']");

        driver.findElement(addToCartLink).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderLink));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(placeOrderLink).click();
    }

    public void validateProductInfo(ProductData product) {
        By cartItemsList = By.cssSelector("li[class = 'cart-item']");
        By nameProduct = By.cssSelector("div.product-line-info a[class = 'label']") ;
        By priceProduct = By.xpath("//*[@id=\"main\"]/div/div[1]/div[1]/div[2]/ul/li/div/div[2]/div[2]/span");
        List<WebElement> allCartItems = driver.findElements(cartItemsList);

        String name = driver.findElement(nameProduct).getText();
        float price = DataConverter.parsePriceValue(driver.findElement(priceProduct).getText());
        Assert.assertEquals(1,allCartItems.size(),"There are more than one item in the cart");
        Assert.assertEquals(name.toUpperCase(),product.getName().toUpperCase(),"Wrong name of the product");
        Assert.assertEquals(price,product.getPrice(),"Wrong price of the product");
    }

    public void proceedOrder() {

        By proceedOrder = By.cssSelector("div.text-xs-center a[class='btn btn-primary']");
        By buyerNameField = By.cssSelector("div.col-md-6 input[name = 'firstname']") ;
        By buyerSurnameField = By.cssSelector("div.col-md-6 input[name = 'lastname']") ;
        By buyerEmailField = By.xpath("//*[@id=\"customer-form\"]/section/div[4]/div[1]/input") ;
        By step2 = By.xpath("//*[@id=\"customer-form\"]/footer/button") ;
        By step3 = By.xpath("//*[@id=\"delivery-address\"]/div/footer/button") ;
        By step4 = By.cssSelector("button[name='confirmDeliveryOption']");
        By buyerAddressField = By.xpath("//*[@id=\"delivery-address\"]/div/section/div[5]/div[1]/input") ;
        By buyerPostField = By.xpath("//*[@id=\"delivery-address\"]/div/section/div[7]/div[1]/input") ;
        By buyerCityField = By.xpath("//*[@id=\"delivery-address\"]/div/section/div[8]/div[1]/input") ;
        By checkPay = By.cssSelector("#payment-option-1-container > span");
        By agree = By.xpath("//*[@id=\"conditions-to-approve\"]/ul/li/div[2]/label");
        By orderButton = By.xpath("//*[@id=\"payment-confirmation\"]/div[1]/button");
        BuyerData buyer = BuyerData.generate();
        wait.until(ExpectedConditions.visibilityOfElementLocated(proceedOrder));
        driver.findElement(proceedOrder).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(buyerNameField));

        driver.findElement(buyerNameField).sendKeys(buyer.getName());
        driver.findElement(buyerSurnameField).sendKeys(buyer.getSurname());
        driver.findElement(buyerEmailField).sendKeys(buyer.getEmail());
        driver.findElement(step2).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(buyerAddressField));
        driver.findElement(buyerAddressField).sendKeys(buyer.getAddress());
        driver.findElement(buyerPostField).sendKeys(buyer.getPost());
        driver.findElement(buyerCityField).sendKeys(buyer.getCity());
        driver.findElement(step3).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        wait.until(ExpectedConditions.visibilityOfElementLocated(step4));
        driver.findElement(step4).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        wait.until(ExpectedConditions.visibilityOfElementLocated(checkPay));
        driver.findElement(checkPay).click();
        driver.findElement(agree).click();
        driver.findElement(orderButton).click();



    }

    public void validateOrder(ProductData product) {

        By confirmLabel = By.xpath("//*[@id=\"content-hook_order_confirmation\"]/div/div/div/h3");
        By orderLineCount = By.xpath("//*[@id=\"order-items\"]/div/div");
        By nameProductLink = By.xpath("//*[@id=\"order-items\"]/div/div/div[2]/span");
        By priceProductLink = By.xpath("//*[@id=\"order-items\"]/div/div/div[3]/div/div[1]");
        Assert.assertEquals(driver.findElement(confirmLabel).getText().substring(1),
                "ВАШ ЗАКАЗ ПОДТВЕРЖДЁН",
                "Wrong confirm label.");
        List<WebElement> orderCount = driver.findElements(orderLineCount);
        Assert.assertEquals(orderCount.size(),1,"Invalid number of items.");
        String nameProduct = driver.findElement(nameProductLink).getText().toUpperCase();
        if (nameProduct.indexOf(" - ") > 0){
            nameProduct = nameProduct.substring(0,nameProduct.indexOf(" - "));
        }
        Assert.assertEquals(nameProduct,
                product.getName().toUpperCase(),
                "Wrong product name in order");

        Assert.assertEquals(DataConverter.parsePriceValue(driver.findElement(priceProductLink).getText()),
                product.getPrice(),
                "Wrong price in order");



    }

    public void checkUpdateInStock(ProductData product) {
        driver.navigate().to(product.getLink());
        driver.findElement(By.cssSelector("a[href = '#product-details']")).click();
        By inStock = By.cssSelector("#product-details > div.product-quantities > span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(inStock));
        int qty = DataConverter.parseStockValue(driver.findElement(inStock).getText());
        Assert.assertEquals(qty, product.getQty()-1,"Wrong qty on stoke.");
    }
}
