package driver;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.BeforeSuite;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Driver {

    // Holds the WebDriver instance
    // public static WebDriver webDriver;
    public static AppiumDriver appiumDriver;
    public static AndroidDriver androidDriver;

    /*    // Initialize a webDriver instance of required browser
        // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
        @BeforeSuite
        public void initializeDriver(){
            webDriver = DriverFactory.getDriver();
        }

        // Close the webDriver instance
        @AfterSuite
        public void closeDriver(){
            webDriver.quit();
        }*/
    @BeforeScenario
    public void initialization() {
        URL url = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "11.0.10");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, "165ebc37");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.getir.casestudy.dev");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "com.getir.casestudy.modules.login.ui.LoginActivity");
        desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.getir.casestudy.modules.splash.ui.SplashActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);

        try {
            url = new URL("http://localhost:4723/wd/hub");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

       /* public void getScreenShot(AppiumDriver<MobileElement> d){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
            Date date = new Date();

        }*/

        appiumDriver = new AppiumDriver(url, desiredCapabilities);
        appiumDriver.manage().timeouts().implicitlyWait(60l, TimeUnit.SECONDS);

    }

    @AfterScenario
    public void close() {
        appiumDriver.quit();
    }

}
