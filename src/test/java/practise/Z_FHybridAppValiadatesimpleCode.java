package practise;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.LongPressOptions;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

public class Z_FHybridAppValiadatesimpleCode {
    @Test
    public void setupDc() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android01");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/Users/ayyildiz/IdeaProjects/AppiumPractiseGithubActions/src/app/General-Store.apk");
        //it wait until elements visible
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);
        URL url = new URL("http://localhost:4723/wd/hub"); //"http://127.0.0.1:4723/wd/hub
        AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(url, desiredCapabilities);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        WebElement countrySpinner = driver.findElementById("com.androidsample.generalstore:id/spinnerCountry");
        countrySpinner.click();

        List<WebElement> countries = driver.findElementsByClassName("android.widget.TextView");

        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Azerbaijan\"))");
        //we shoul click selcted country
        driver.findElementByXPath("//*[@text='Azerbaijan']").click();


        WebElement nameBox = driver.findElementById("com.androidsample.generalstore:id/nameField");
        nameBox.sendKeys("ali");
        // we need to hide keybord so use this method :)
        driver.hideKeyboard();

        //  WebElement genderMale = driver.findElementById("com.androidsample.generalstore:id/radioMale");
        WebElement genderMale = driver.findElementByXPath("//*[@text='Male']");
        genderMale.click();


        WebElement letsShopButton = driver.findElementById("com.androidsample.generalstore:id/btnLetsShop");
        letsShopButton.click();
        Thread.sleep(3000);
        WebElement titleText = driver.findElementById("com.androidsample.generalstore:id/toolbar_title");
        Assert.assertEquals("Products", titleText.getText());
//we are on second page

        driver.findElementsByXPath("//*[@text='ADD TO CART']").get(0).click();

        driver.findElementsByXPath("//*[@text='ADD TO CART']").get(0).click();

        //lets validate item in checkout page:
        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
        Thread.sleep(2000);

        List<WebElement> itemsPrice = driver.findElementsById("com.androidsample.generalstore:id/productPrice");
        double sum = 0;
      //  String firstItemPrice = itemsPrice.get(0).getText();
        //lets put in a container
        // you can get 1 2 3 item so on
     //   double ItemPrice1 =getAmount(firstItemPrice);
       // lets make it more dynamic
        for (int i =0; i<itemsPrice.size(); i++){
            String stringItemPrice = itemsPrice.get(i).getText();
           double itemPrice = getAmount(stringItemPrice);
           //how to sum up lets create a variable sum
            sum=sum+itemPrice; //it will get 1 2 3 ... item price and sum up  we can add o mere item to cart
        }
        //Lets see expected sum
        System.out.println("Expected total price is " +sum);

        WebElement actualTotalPrice = driver.findElementById("com.androidsample.generalstore:id/totalAmountLbl");
        String actualTotalPriceText = actualTotalPrice.getText();

        //let s convert it to int
        double doubleActualTotalPrice = Double.parseDouble(actualTotalPriceText.substring(1));
        System.out.println("Actual total is " +doubleActualTotalPrice);
        //or use method
     //   double doubleActualTotalPrice = getAmount(actualTotalPriceText);
        //lets validate
        Assert.assertEquals(sum,doubleActualTotalPrice,0.0);
        System.out.println("it is solved");


 //let make our code clean  copy and past ths class

        //Let finis the page with mobile gesture
        //click check box there is only one checkbocx so lets take calssname

        WebElement checkBox = driver.findElementByClassName("android.widget.CheckBox");
//        checkBox.click();
        //or
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(tapOptions().withElement(element(checkBox))).perform();

        WebElement termsNConditions = driver.findElementById("com.androidsample.generalstore:id/termsButton");
        touchAction.longPress(LongPressOptions.longPressOptions().
                withElement(element(termsNConditions)).
                withDuration(Duration.ofSeconds(2))).release().perform();

        WebElement closeButton = driver.findElementById("android:id/button1");
        Assert.assertTrue(closeButton.isDisplayed());

        closeButton.click();
        //convert to webview
        driver.findElementById("com.androidsample.generalstore:id/btnProceed").click();



    }
    //lets create static method for parsDouble
    //this method will take care about converting string to double
    public static double getAmount(String value) {
        value = value.substring(1);
        double valueToAmount = Double.parseDouble(value);
        return valueToAmount;
    }
}
