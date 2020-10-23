package steps;

import com.thoughtworks.gauge.Step;
import driver.Driver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import methods.Methods;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.Random;

public class StepImplementation extends Driver {

    protected static Methods methods;
    public static Logger logger = Logger.getLogger(StepImplementation.class);
    FluentWait<AppiumDriver<MobileElement>> fluentWait;
    public String userName = "com.getir.casestudy.dev:id/usernameEditText";
    public String password = "com.getir.casestudy.dev:id/passwordEditText";
    public String loginButton = "com.getir.casestudy.dev:id/loginButton";
    public String loginUserLogo = "com.getir.casestudy.dev:id/ga_toolbar_leftIconImageView";
    public String logoutButton = "com.getir.casestudy.dev:id/itemLogout";
    public String food = "(//android.widget.TextView[@resource-id='com.getir.casestudy.dev:id/tvTitle'])[3]";
    public String milkAndBreakfast = "(//android.widget.TextView[@resource-id='com.getir.casestudy.dev:id/tvTitle'])[6]";
    public String baby = "(//android.widget.TextView[@resource-id='com.getir.casestudy.dev:id/tvTitle'])[12]";
    public String firstProduct = "(//android.widget.ImageView[@resource-id='com.getir.casestudy.dev:id/btnAdd'])[1]";
    public String firstProductPrice = "(//android.widget.TextView[@resource-id='com.getir.casestudy.dev:id/tvPrice'])[1]";
    public String firstProductCount = "com.getir.casestudy.dev:id/tvCount";
    public String basketIcon = "com.getir.casestudy.dev:id/ga_toolbar_getir10GABasketButton";
    public String productInBasketCount = "com.getir.casestudy.dev:id/tvCount";
    public String productInBasketPrice = "com.getir.casestudy.dev:id/tvPrice";
    public String removeProduct = "com.getir.casestudy.dev:id/btnRemove";
    public String basketText = "com.getir.casestudy.dev:id/tvTitle";
    public String addProduct = "com.getir.casestudy.dev:id/btnAdd";
    public String paymentButton = "com.getir.casestudy.dev:id/btnCheckout";
    public String paymentPageTitle = "com.getir.casestudy.dev:id/ga_toolbar_getir10TitleTextView";
    public String totalAmount = "com.getir.casestudy.dev:id/tvAmount";
    public String backButton = "com.getir.casestudy.dev:id/ga_toolbar_leftIconImageView";


    @Step("Login with <key> and <key>")
    public void loginWithInfo(String keyUserName, String keyPassword) {
        //fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userName)));
        loginProcess(keyUserName, keyPassword);

        try {
            MobileElement mobileElementLogo = (MobileElement) appiumDriver.findElementById(loginUserLogo);
            logger.info("login successful");
        } catch (Exception e) {
            methods.takeScreenShot();
            Assert.fail("login failed");
            e.printStackTrace();
        }

    }

    @Step("Failed login with <key> and <key>")
    public void failedLoginWithInfo(String keyUserName, String keyPassword) {
        //fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(userName)));
        loginProcess(keyUserName, keyPassword);

        MobileElement mobileElementLoginPageElement = (MobileElement) appiumDriver.findElementById(loginButton);
        if (mobileElementLoginPageElement.isDisplayed()) {
            logger.info("login fail - SUCCESS");
            methods.takeScreenShot();
        } else {
            logger.info("login fail - FAIL");
            Assert.fail("login success");
        }

    }

    @Step("Logout from application")
    public void logout() {
        MobileElement mobileElementLogo = (MobileElement) appiumDriver.findElementById(loginUserLogo);
        mobileElementLogo.click();

        MobileElement mobileElementLogoutButton = (MobileElement) appiumDriver.findElementById(logoutButton);
        mobileElementLogoutButton.click();

        try {
            MobileElement user = (MobileElement) appiumDriver.findElementById(userName);
            logger.info("logout successful");
        } catch (Exception e) {
            methods.takeScreenShot();
            Assert.fail("logout failed");
            e.printStackTrace();
        }
    }

    int price = 0;
    int count = 0;

    @Step("Randomly one product add to basket")
    public void addBasket() {
        String[] arr = {"Yiyecek", "Süt & Kahvaltı", "Bebek"};
        Random random = new Random();
        int num = random.nextInt(arr.length);
        MobileElement category = null;

        switch (num) {
            case 0:
                category = (MobileElement) appiumDriver.findElementByXPath(food);
                break;
            case 1:
                category = (MobileElement) appiumDriver.findElementByXPath(milkAndBreakfast);
                break;
            case 2:
                //  swipeToPhone(100, 100, 100, 300, 3000);
                category = (MobileElement) appiumDriver.findElementByXPath(baby);
                break;
        }

        category.click();
        MobileElement mobileElementFirstProduct = (MobileElement) appiumDriver.findElementByXPath(firstProduct);
        mobileElementFirstProduct.click();

        MobileElement mobileElementFirstProductPrice = (MobileElement) appiumDriver.findElementByXPath(firstProductPrice);
        price = Integer.parseInt(mobileElementFirstProductPrice.getText().split("\\s+")[0]);
        MobileElement mobileElementFirstProductCount = (MobileElement) appiumDriver.findElementById(firstProductCount);
        count = Integer.parseInt(mobileElementFirstProductCount.getText());
        Assert.assertEquals(1, count);
        logger.info("Product price: " + price);
    }

    int basketCount = 0;
    int basketPrice = 0;

    @Step("Go to basket and control")
    public void basket() {
        MobileElement mobileElementBasket = (MobileElement) appiumDriver.findElementById(basketIcon);
        mobileElementBasket.click();
        MobileElement mobileElementBasketCount = (MobileElement) appiumDriver.findElementById(productInBasketCount);
        basketCount = Integer.parseInt(mobileElementBasketCount.getText());
        Assert.assertEquals(count, basketCount);

        MobileElement mobileElementBasketPrice = (MobileElement) appiumDriver.findElementById(productInBasketPrice);
        basketPrice = Integer.parseInt(mobileElementBasketPrice.getText().split("\\s+")[0]);
        Assert.assertEquals(price, basketPrice);

    }

    @Step("Remove products")
    public void removeFromBasket() {
        MobileElement mobileElementRemove = (MobileElement) appiumDriver.findElementById(removeProduct);
        mobileElementRemove.click();

        MobileElement mobileElementBasketText = (MobileElement) appiumDriver.findElementById(basketText);
        Assert.assertEquals("There is no result", mobileElementBasketText.getText());
        logger.info("Basket is empty");
    }

    @Step("Add <key> product")
    public void addProduct(int key) {
        MobileElement mobileElementAddProduct = (MobileElement) appiumDriver.findElementById(addProduct);
        for (int i = 1; i <= key; i++) {
            methods.waitBySeconds(1);
            mobileElementAddProduct.click();
        }
        logger.info("Added " + key + " product");

        MobileElement mobileElementBasketCount = (MobileElement) appiumDriver.findElementById(productInBasketCount);
        Assert.assertEquals(key + 1, Integer.parseInt(mobileElementBasketCount.getText()));
        logger.info("Total number of products: " + Integer.parseInt(mobileElementBasketCount.getText()));

    }

    @Step("Go to payment page")
    public void goToPaymentPage() {
        MobileElement mobileElementPaymentButton = (MobileElement) appiumDriver.findElementById(paymentButton);
        mobileElementPaymentButton.click();

        methods.waitBySeconds(2);
        MobileElement mobileElementPaymentPageTitle = (MobileElement) appiumDriver.findElementById(paymentPageTitle);
        Assert.assertEquals(true, mobileElementPaymentPageTitle.isDisplayed());

        int amount = basketPrice * 2;
        MobileElement mobileElementTotalAmount = (MobileElement) appiumDriver.findElementById(totalAmount);
        Assert.assertEquals(amount, Integer.parseInt(mobileElementTotalAmount.getText().split("\\s+")[0]));
        logger.info("Total amount: " + Integer.parseInt(mobileElementTotalAmount.getText().split("\\s+")[0]));

    }

    @Step("Empty basket")
    public void emptyBasket() {
        MobileElement mobileElementBackButton = (MobileElement) appiumDriver.findElementById(backButton);
        mobileElementBackButton.click();

        MobileElement mobileElementFirstProductCount = (MobileElement) appiumDriver.findElementById(firstProductCount);
        int numberOfProduct = Integer.parseInt(mobileElementFirstProductCount.getText());

        MobileElement mobileElementRemoveProduct = (MobileElement) appiumDriver.findElementById(removeProduct);

        for (int i = 0; i < numberOfProduct; numberOfProduct--) {
            methods.waitBySeconds(1);
            mobileElementRemoveProduct.click();
        }

        MobileElement mobileElementBasketText = (MobileElement) appiumDriver.findElementById(basketText);
        Assert.assertEquals("There is no result", mobileElementBasketText.getText());
        logger.info("Basket is empty");
    }


    @Step("Wait <key> seconds")
    public void waitWhileSeconds(int key) {
        methods.waitBySeconds(key);
    }

    private void loginProcess(String keyUserName, String keyPassword) {
        MobileElement mobileElementUserName = (MobileElement) appiumDriver.findElementById(userName);
        mobileElementUserName.click();
        mobileElementUserName.sendKeys(keyUserName);

        MobileElement mobileElementPassword = (MobileElement) appiumDriver.findElementById(password);
        mobileElementPassword.click();
        mobileElementPassword.sendKeys(keyPassword);

        MobileElement mobileLoginButton = (MobileElement) appiumDriver.findElementById(loginButton);
        mobileLoginButton.click();
    }

    public StepImplementation(){
        methods = new Methods();
    }

}
