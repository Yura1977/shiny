package myprojects.automation.assignment3.tests;
import myprojects.automation.assignment3.BaseScript;
import myprojects.automation.assignment3.GeneralActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CreateCategoryTest extends BaseScript {
    private static final String user = "webinar.test@gmail.com";
    private static String pass = "Xcg7299bnSmMuRLp9ITw";



    public static void main(String[] args) throws InterruptedException {

        // TODO prepare driver object
         WebDriver driver = getConfiguredDriver();
        String newCategory = "Hi";
         GeneralActions actions = new GeneralActions(driver);

            // Enter to the site prestashop-automation
            // login
        actions.login(user,pass);


        // create category
        actions.createCategory(newCategory);

        // check that new category appears in Categories table
        List<WebElement> cats = actions.findCatgoties(newCategory);

        if (cats.size() == 0) throw new AssertionError("Category <" + newCategory + "> not created!");

        for(WebElement e : cats)
            System.out.println("Found category " + newCategory + " with id " + e.getText());

        // finish script
        driver.quit();
    }
}
