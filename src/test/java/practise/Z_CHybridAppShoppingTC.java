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

public class Z_CHybridAppShoppingTC {
    /*
    tc
    shop specific products items in the app and add to cart
    shop items in the app by scrolling to specific product and add to cart
    if the product is available so scroll to that product add to cart and verify it is added to cart

     */
    @Test
    public void setupDc() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android01");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        desiredCapabilities.setCapability(MobileCapabilityType.APP,"/Users/mehmetaliayyildiz/IdeaProjects/AppiumCucumberByAyyildiz/src/Apps/General-Store.apk");
        //it wait until elements visible
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT,15);
        //       desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET,"true");
        URL url = new URL("http://localhost:4723/wd/hub"); //"http://127.0.0.1:4723/wd/hub"
        AndroidDriver<WebElement> driver = new AndroidDriver<WebElement>(url, desiredCapabilities);

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        WebElement countrySpinner = driver.findElementById("com.androidsample.generalstore:id/spinnerCountry");
        countrySpinner.click();

        List<WebElement> countries = driver.findElementsByClassName("android.widget.TextView");
        System.out.println(countries.size());

        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"France\"))") ;
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
        Assert.assertEquals("Products",titleText.getText());
//we are on second page
       //1st way
        //text is unique /id is same put them list and find it from text
        //when we see common name we are putting in the list
        //you can not do it why? bcz appium will see only threat those product if you see 2 product it will store only 2
        //but it is not true we have more but they are not in visible mode
        //how do you handle? scrollIntoView() but may be it can be problem why? let me show you
        //get produc untill text visible but add to cart is not visible yet :(
        //we told to appium scroll until text visible it is not enough for me why it is scrolling then...
        //How to solve this?
        //same step but different way:
        //scrolllableComponent in adnroid and find it get id
        //what we can get from there it is saying taht i do not deal with list but list element
        // so scroll it until element completely visible so i can do what ever i want.
        //so it is in this way i ll find parent and go to child
        //parent? android native apps if you want to scroll there is a mekanizm which have enabkikty of scroll
        // it is called in android scrollable component when you select it will select all products in the window
        // bcz of this we should use this method...

        driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\"Jordan 6 Rings\").instance(0))");
        //add cart click
        //there is an other problem  now text same id same how to locate element "add to cart"
        //scroll is scrolling it will not tell us where to stop ... so we can not make consist index
        //put in list then put fir loop
        List<WebElement> products = driver.findElementsById("com.androidsample.generalstore:id/productName");
        for(int i = 0; i<products.size(); i++){
           String productName = driver.findElementsById("com.androidsample.generalstore:id/productName").get(i).getText();
           if (productName.equals("Jordan 6 Rings")){
            //   whenever get this element it does not matter index 0 or 1 or 2
               // i will click add to cart button
               driver.findElementsById("com.androidsample.generalstore:id/productAddCart").get(i).click();
               break;
           }
        }
        driver.findElementById("com.androidsample.generalstore:id/appbar_btn_cart").click();
    }
}
