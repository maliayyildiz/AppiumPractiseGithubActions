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

public class Z_AHybridAppPositive {
    /*
    select contry by scrolling
    provide name
    hide keybord??
    select gender
    lets shopp
    verify next page
    negative
    blank name
    verify popup displayed
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


    }
}
