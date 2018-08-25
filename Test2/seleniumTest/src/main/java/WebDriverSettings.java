import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public abstract class WebDriverSettings {
    public static WebDriver getInitFirefoxDriver() {

        System.setProperty ("webdriver.gecko.driver", LoginTest.class.getResource("geckodriver.exe").getPath());
        return  new FirefoxDriver();
        //System.setProperty ("webdriver.gecko.driver",LoginTest.class.getResource("geckodriver.exe").getPath());

        // return new FirefoxDriver();
    }
}
