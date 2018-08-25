import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckMainMenuTest extends WebDriverSettings {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = getInitFirefoxDriver();
        driver.get("http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/");
        driver.manage().window().maximize();
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("passwd"));
        WebElement button = driver.findElement(By.name("submitLogin"));
        email.sendKeys(new CharSequence[]{"webinar.test@gmail.com"});
        password.sendKeys(new CharSequence[]{"Xcg7299bnSmMuRLp9ITw"});
        button.click();
        Thread.sleep(3000L);

        WebElement dashboardLink = driver.findElement(By.cssSelector("a.title"));
        WebElement statLink = driver.findElement(By.cssSelector("#subtab-AdminStats > a"));
        List<WebElement> menuItems = driver.findElements(By.cssSelector("a.title.has_submenu"));

       /* menuItems.add(0,  dashboardLink);

        List <String> links = new ArrayList<String>();
        for (WebElement i: menuItems){
            links.add(i.getAttribute("href"));

        }
        for (String s: links){
            driver.get(s);

        }*/

        menuItems.add(0,dashboardLink);
        menuItems.add(5,statLink);
        List<String> links = new ArrayList();
        Iterator iter = menuItems.iterator();

        while (iter.hasNext()) {
            WebElement i;
            i = (WebElement) iter.next();
            links.add(i.getAttribute("href"));
        }

        iter = links.iterator();

        while (iter.hasNext()) {
            String s = (String) iter.next();
            driver.get(s);
            String title = driver.findElement(By.tagName("h2")).getText();
            System.out.println(title);
            Thread.sleep(1000L);
            driver.navigate().refresh();
            Thread.sleep(1000L);
            if (title.equals(driver.findElement(By.tagName("h2")).getText())) {
                System.out.println("Passed");
            } else {
                System.out.println("Failed");
            }
        }
        driver.quit();
    }
}





