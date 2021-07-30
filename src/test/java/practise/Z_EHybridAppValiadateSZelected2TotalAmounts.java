package practise;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Z_EHybridAppValiadateSZelected2TotalAmounts {
    @Test
    public void setupDc() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android01");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, "/Users/mehmetaliayyildiz/IdeaProjects/AppiumCucumberByAyyildiz/src/Apps/General-Store.apk");
        //it wait until elements visible
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 15);
        //       desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET,"true");
        URL url = new URL("http://localhost:4723/wd/hub"); //"http://127.0.0.1:4723/wd/hub"
        AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(url, desiredCapabilities);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        WebElement countrySpinner = driver.findElementById("com.androidsample.generalstore:id/spinnerCountry");
        countrySpinner.click();

        List<WebElement> countries = driver.findElementsByClassName("android.widget.TextView");
        //System.out.println(countries.size());

        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"France\"))");
        //we shoul click selcted country
        driver.findElementByXPath("//*[@text='France']").click();


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

//i ll show show you real scaniaro again find it by text and Lets see

       // driver.findElementsByXPath("//*[@text='ADD TO CART']");
        //when you put size commect out up then
        driver.findElementsByXPath("//*[@text='ADD TO CART']").get(0).click();
      //  driver.findElementsByXPath("//*[@text='ADD TO CART']").get(1).click();
        //it will fail
        //our logic and codes are correct but it failed why?
        //bcz when it click fir button it is not anymore "ADD TO CART" so it will check anfd see only 1 item so it mean index only 0
        //not 0 and 1 ...
        //so make it index 0 again :))) this is the trick you wiil get this with experience so work on such a scenario again
        //put size between click and see size?
        //then makke both size 0 and run
        driver.findElementsByXPath("//*[@text='ADD TO CART']").get(0).click();
        //or put in a container that s all
//         List<WebElement> products = driver.findElementsByXPath("//*[@text='ADD TO CART']");
//         System.out.println("When page available displayed products are "+products.size());//2
        //lets click add to cart
//         products.get(0).click();
//         products.get(1).click();
        //it will be ok then bcz we are working list which we created before clicking

        //lets validate item in checkout page:
        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
        Thread.sleep(2000);

        List<WebElement> itemsPrice = driver.findElementsById("com.androidsample.generalstore:id/productPrice");
        String firstItemPrice = itemsPrice.get(0).getText();
        System.out.println("First item price is "+firstItemPrice);
        String secondItemPrice = itemsPrice.get(1).getText();
        System.out.println("Second item price is "+secondItemPrice);
        //in this stuation can i make calculation
        //it must be numeric value how to do it???
        //let me update must string variable
        firstItemPrice =firstItemPrice.substring(1); //but it is string :)
        secondItemPrice =secondItemPrice.substring(1);
        // lets cats them to double (why double bcz there is decimal)  do you  remmeber????
        double price1 = Double.parseDouble(firstItemPrice);
        System.out.println("Now price is int value " +price1);
        double price2 = Double.parseDouble(secondItemPrice);
        System.out.println("Now price is int value " +price2);

        double expectedTotalPrice = price1+price2;
        System.out.println("Expected total price is " +expectedTotalPrice);

        WebElement actualTotalPrice = driver.findElementById("com.androidsample.generalstore:id/totalAmountLbl");
        String actualTotalPriceText = actualTotalPrice.getText();

        //let s convert it to int
        double intActualTotalPrice = Double.parseDouble(actualTotalPriceText.substring(1));
        System.out.println("Actual total proce " +intActualTotalPrice);
        //lets validate
        Assert.assertEquals(expectedTotalPrice,intActualTotalPrice,0.0);
        System.out.println("it is solved");

//show the picture
 //let make our code clean

    }
}
