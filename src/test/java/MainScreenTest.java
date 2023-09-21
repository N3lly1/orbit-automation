import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.testng.Assert.*;

public class MainScreenTest {

    AndroidDriver androidDriver;

    @BeforeTest
    public void before() {
        File applicationParentDirectoryPath = new File("src");
        File app = new File(applicationParentDirectoryPath, "simple-todo.apk");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(DEVICE_NAME, "OnePlus_LE2115");
        desiredCapabilities.setCapability(APP, app.getAbsolutePath());
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        desiredCapabilities.setCapability("ignoreHiddenApiPolicyError", true);

        try {
            androidDriver = new AndroidDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test(priority=1)
    public void addToDoTest() {
        addToDo();

        WebElement orbitTestElement = androidDriver.findElement(AppiumBy.xpath("//android.widget.CheckBox[@content-desc=\"Orbit test\"]"));
        assertEquals(orbitTestElement.getAttribute("content-desc"), "Orbit test");
    }

    @Test(priority=2)
    public void editToDoTest() {
        tapByCoordinates(940, 351);
        tapByCoordinates(953, 524);

        androidDriver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.widget.EditText")).clear();
        androidDriver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.widget.EditText")).sendKeys("Orbit test edit");
        androidDriver.findElement(AppiumBy.accessibilityId("Edit")).click();

        WebElement orbitTestElement = androidDriver.findElement(AppiumBy.xpath("//android.widget.CheckBox[@content-desc=\"Orbit test edit\"]"));
        assertEquals(orbitTestElement.getAttribute("content-desc"), "Orbit test edit");
    }

    @Test(priority=3)
    public void deleteToDoTest() {
        tapByCoordinates(940, 351);
        tapByCoordinates(957, 672);

        int elementsSize = androidDriver.findElements(AppiumBy.xpath("//android.widget.CheckBox[@content-desc=\"Orbit test edit\"]")).size();
        assertEquals(elementsSize, 0);
    }

    private void addToDo() {
        androidDriver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.Button")).click();
        androidDriver.findElement(AppiumBy.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View[1]/android.view.View/android.view.View/android.widget.EditText")).sendKeys("Orbit test");
        androidDriver.findElement(AppiumBy.accessibilityId("Add")).click();
    }

    private void tapByCoordinates(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence clickToDoOptions = new Sequence(finger, 1);
        clickToDoOptions.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        androidDriver.perform(Arrays.asList(clickToDoOptions));
    }
}
