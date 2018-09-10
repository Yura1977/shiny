package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        // TODO open main page and validate website version
        driver.navigate().to(Properties.getBaseUrl());
        if (isMobileTesting){

            Assert.assertTrue(isElementPresent(By.cssSelector("#_mobile_top_menu #top-menu")),
                    "Incorrect version of the site.");
        } else {

            Assert.assertTrue(isElementPresent(By.cssSelector("#_desktop_logo a")),
                    "Incorrect version of the site.");
        }

    }
        /**
         * Check if element present on the web page.
         * @param locator
         * @return true if element present, else false
         */
    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }



    @Test
    public void createNewOrder() {
        //implement order creation test

        driver.navigate().to(Properties.getBaseUrl());
        // open random product
        actions.openRandomProduct();

        // save product parameters
        ProductData product = actions.getOpenedProductInfo();
        product.setLink(driver.getCurrentUrl());

        // add product to Cart and validate product information in the Cart
        actions.addProductToCart();
        actions.validateProductInfo(product);

        // proceed to order creation, fill required information
        actions.proceedOrder();

        // place new order and validate order summary
        actions.validateOrder(product);
        // check updated In Stock value
        actions.checkUpdateInStock(product);
    }

}
