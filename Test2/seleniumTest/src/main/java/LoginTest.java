import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
public class LoginTest extends WebDriverSettings {
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
        sleep(4000);
        WebElement icon = driver.findElement(By.id("employee_infos"));
        WebElement logout = driver.findElement(By.id("header_logout"));
        icon.click();
        sleep(1000);
        logout.click();
        sleep(1000);
        driver.quit();
    }
    public static void sleep(int s) throws InterruptedException {
    Thread.sleep (s);
    }
}