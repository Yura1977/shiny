package myprojects.automation.assignment3;

import myprojects.automation.assignment3.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Timestamp;
import java.util.List;

/**
 * Contains main script actions that may be used in scripts.
 */
public  class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    //login page
    private final By loginInput;
    private final By passwdInput = By.id("passwd");
    private final By submitButton = By.name("submitLogin");

    //dashboard page commons
    private final By spinnerSpan = By.id("ajax_running");
    private final By subtabCatalog = By.id("subtab-AdminCatalog");
    private final By subtabCategoties = By.id("subtab-AdminCategories");

    //categories related pages
    private final By newCatButton = By.cssSelector("a#desc-category-new");
    private final By catNameInput = By.id("name_1");
    private final By catCreateOK = By.className("alert-success");
    private final By filterInput = By.name("categoryFilter_name");
    private final By filterButton = By.id("submitFilterButtoncategory");

    private final String newCatIDs = "//table[@id='table-category']/tbody/tr/td[contains(text(),'%s')]/../td[2]";




    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        loginInput = By.id("email");
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        driver.get(Properties.getBaseAdminUrl());

        wait.until(ExpectedConditions.visibilityOfElementLocated(loginInput));

        driver.findElement(loginInput).sendKeys(login);
        driver.findElement(passwdInput).sendKeys(password);
        driver.findElement(submitButton).click();

        waitForContentLoad();
    }

    /**
     * Adds new category in Admin Panel.
     * @param categoryName
     */
    public void createCategory(String categoryName) {
        goToCatList();

        driver.findElement(newCatButton).click();
        waitForContentLoad();

        WebElement name = driver.findElement(catNameInput);
        name.sendKeys(categoryName);
        name.sendKeys(Keys.TAB); //Подбор ЧПУ
        name.submit();
        waitForContentLoad();

        wait.until(ExpectedConditions.visibilityOfElementLocated(catCreateOK));

    }

    public List<WebElement> findCatgoties(String categoryName) {
        goToCatList();

        driver.findElement(filterInput).sendKeys(categoryName);
        driver.findElement(filterButton).click();
        waitForContentLoad();

        By selector = By.xpath(String.format(newCatIDs, categoryName));
        return driver.findElements(selector);
    }

    public void goToCatList() {
        new Actions(driver)
                .moveToElement(driver.findElement(subtabCatalog))
                .perform();
        wait.until(ExpectedConditions.elementToBeClickable(subtabCategoties));
        driver.findElement(subtabCategoties).click();
        waitForContentLoad();
    }

    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Initially bellow given if condition will check ready state of page.

        wait.until((ExpectedCondition<Boolean>) wd ->
                ( js).executeScript("return document.readyState").equals("complete"));


        wait.until(ExpectedConditions.visibilityOfElementLocated(spinnerSpan));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(spinnerSpan));
    }

}
