//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package myprojects.automation.assignment4;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import myprojects.automation.assignment4.utils.logging.EventHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public abstract class BaseTest {
    protected EventFiringWebDriver driver;
    protected GeneralActions actions;

    public BaseTest() {
    }

    private WebDriver getDriver(String browser) {
        byte var3 = -1;
        switch(browser.hashCode()) {
            case -1361128838:
                if (browser.equals("chrome")) {
                    var3 = 3;
                }
                break;
            case -849452327:
                if (browser.equals("firefox")) {
                    var3 = 0;
                }
                break;
            case -536147394:
                if (browser.equals("internet explorer")) {
                    var3 = 2;
                }
                break;
            case 3356:
                if (browser.equals("ie")) {
                    var3 = 1;
                }
        }

        switch(var3) {
            case 0:
                System.setProperty("webdriver.gecko.driver", this.getResource("/geckodriver.exe"));
                return new FirefoxDriver();
            case 1:
            case 2:
                DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability("ignoreProtectedModeSettings", true);
                capabilities.setCapability("requireWindowFocus", true);
                capabilities.setCapability("credentials_enable_service", false);
                System.setProperty("webdriver.ie.driver", this.getResource("/IEDriverServer.exe"));
                return new InternetExplorerDriver(capabilities);
            case 3:
            default:
                System.setProperty("webdriver.chrome.driver", this.getResource("/chromedriver.exe"));
                Map<String, Object> prefs = new LinkedHashMap();
                prefs.put("credentials_enable_service", false);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", prefs);
                options.addArguments(new String[]{"chrome.switches", "--disable-infobars"});
                return new ChromeDriver(options);
        }
    }

    private String getResource(String resourceName) {
        try {
            return Paths.get(BaseTest.class.getResource(resourceName).toURI()).toFile().getPath();
        } catch (URISyntaxException var3) {
            var3.printStackTrace();
            return resourceName;
        }
    }

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(String browser) {
        this.driver = new EventFiringWebDriver(this.getDriver(browser));
        this.driver.register(new EventHandler() {
            public void beforeAlertAccept(WebDriver webDriver) {
            }

            public void afterAlertAccept(WebDriver webDriver) {
            }

            public void afterAlertDismiss(WebDriver webDriver) {
            }

            public void beforeAlertDismiss(WebDriver webDriver) {
            }
        });
        this.driver.manage().timeouts().implicitlyWait(25L, TimeUnit.SECONDS);
        this.driver.manage().timeouts().pageLoadTimeout(25L, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
        this.actions = new GeneralActions(this.driver, this.actions);
    }

    @AfterClass
    public void tearDown() throws InterruptedException {
        if (this.driver != null) {
            Thread.sleep(3000L);
            this.driver.quit();
        }

    }
}
