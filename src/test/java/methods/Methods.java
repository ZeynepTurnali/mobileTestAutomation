package methods;

import driver.Driver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import steps.StepImplementation;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Methods extends Driver {

    public static Logger logger = Logger.getLogger(Methods.class);

    public void waitBySeconds(long seconds) {

        logger.info(seconds + " seconds waiting...");
        waitByMilliSeconds(seconds * 1000);
    }

    public void waitByMilliSeconds(long milliSeconds) {

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void takeScreenShot() {
        File file = ((TakesScreenshot) appiumDriver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File("Screenshot.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swipeToPhone(int startX, int startY, int endX, int endY, long duration) {

        Dimension phoneSize = appiumDriver.manage().window().getSize();
        int phoneWidth = phoneSize.width;
        int phoneHeight = phoneSize.height;
        startX = (phoneWidth * startX) / 100;
        startY = (phoneHeight * startY) / 100;
        endX = (phoneWidth * endX) / 100;
        endY = (phoneHeight * endY) / 100;

        TouchAction action = new TouchAction(appiumDriver);
        action.press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration)))
                .moveTo(PointOption.point(endX, endY))
                .release().perform();
    }
}
