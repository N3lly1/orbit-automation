import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.MobileCapabilityType.*;

public class Base {

    public static void main(String[] args) {
        File applicationParentDirectoryPath = new File("src");
        File app = new File(applicationParentDirectoryPath, "simple-todo.apk");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(DEVICE_NAME, "OnePlus_LE2115");
        desiredCapabilities.setCapability(APP, app.getAbsolutePath());
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability("ignoreHiddenApiPolicyError" , true);

        try {
            AndroidDriver androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
