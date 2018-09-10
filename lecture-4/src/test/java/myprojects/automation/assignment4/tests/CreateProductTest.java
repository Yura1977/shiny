//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {
    private ProductData product = ProductData.generate();

    public CreateProductTest() {
    }

    @DataProvider(
            name = "Authentication"
    )
    public static Object[][] credentials() {
        return new Object[][]{{"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}};
    }

    @Test(
            dataProvider = "Authentication"
    )
    public void createNewProduct(String login, String password) {
        this.actions.login(login, password);
        this.actions.goToCreateNewProduct();
        this.actions.createProduct(this.product);
    }

    @Test(
            dependsOnMethods = {"createNewProduct"}
    )
    public void checkProduct() throws InterruptedException {
        this.actions.open();
        this.actions.checkProduct(this.product);
        Thread.sleep(1000L);
    }
}
