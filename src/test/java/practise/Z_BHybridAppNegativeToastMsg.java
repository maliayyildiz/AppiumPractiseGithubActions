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

public class Z_BHybridAppNegativeToastMsg {
    /*
    toast message lik spy object
    android.widget.Toast general class name
    if you multiple toast like 2 it means first toast msg [1] second  is [2]
    index start 1 2 not 0 in appium :)
    class name you can us e as tag name ***
    when devs developed toast message dev give name attirubute has content(text) will be there
    this is devs basics...
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


//for negative TC do not provide name
        WebElement nameBox = driver.findElementById("com.androidsample.generalstore:id/nameField");
       // nameBox.sendKeys("ali");
        // we need to hide keybord so use this method :)
      //  driver.hideKeyboard();

        //  WebElement genderMale = driver.findElementById("com.androidsample.generalstore:id/radioMale");
        WebElement genderMale = driver.findElementByXPath("//*[@text='Male']");
        genderMale.click();


        WebElement letsShopButton = driver.findElementById("com.androidsample.generalstore:id/btnLetsShop");
        letsShopButton.click();

        WebElement toastMessage = driver.findElementByXPath("//android.widget.Toast[1]");
        System.out.println(toastMessage.getAttribute("name"));
        Assert.assertEquals("Please enter your name",toastMessage.getAttribute("name"));

//this is actual validation

    }
}

