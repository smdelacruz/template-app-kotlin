import android.AndroidPageObjectManager
import io.appium.java_client.AppiumDriver
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import io.qameta.allure.Allure
import ios.IosPageObjectManager
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.remote.DesiredCapabilities
import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.annotations.*
import utilities.*
import java.net.URL

open class TestBase {
    lateinit var driver: AppiumDriver
    lateinit var iospages: IosPageObjectManager
    lateinit var androidpages: AndroidPageObjectManager
    private val caps = DesiredCapabilities()
    private val logger = CustomLogger(this::class.java)     // Initialize your custom logger
    val testdevice = testdata(System.getProperty("testDevice"))
//    private val appcenter = // access app here
    var os: String? = testdevice?.platform.toString().uppercase()

    @BeforeSuite
    fun setUp(testContext: ITestContext) {
        // ToDo: Automation Starts here
        val appiumurl = URL("http://127.0.0.1:4723") //ServerAppium.start()

        // Based on the parameter when test was run, we checked with os to run on deviceEnvironment.json
        if (os == OS.ANDROID.toString()) {
            caps.setCapability("autoAcceptAlerts", true)
            caps.setCapability("platformName", testdevice?.platform)
            caps.setCapability("automationName", testdevice?.automationName)
            caps.setCapability("deviceName", testdevice?.deviceName)
            caps.setCapability("platformVersion", testdevice?.version)
            caps.setCapability("appPackage", "") // ToDo: appPackage here
            caps.setCapability("clearSystemFiles", true)
            caps.setCapability("appium:disableIdLocatorAutocompletion", true)
            driver = AndroidDriver(appiumurl, caps)
            androidpages = AndroidPageObjectManager(driver)
        } else {
            caps.setCapability("ignoreUnimportantViews", true)
            caps.setCapability("unicodeKeyboard", true)
            caps.setCapability("resetKeyboard", true)
            caps.setCapability("autoAcceptAlerts", true)
            caps.setCapability("autoDismissAlerts", true)
            caps.setCapability("platformName", testdevice?.platform)
            caps.setCapability("automationName", testdevice?.automationName)
            caps.setCapability("deviceName", testdevice?.deviceName)
            caps.setCapability("platformVersion", testdevice?.version)
            caps.setCapability("udid", testdevice?.deviceUdId)
            caps.setCapability("appium:xcodeOrgId", "") // ToDo: xcode orgid here
            caps.setCapability("appium:xcodeSigningId", "Developer")
            caps.setCapability("wdaLocalPort", "8100".toInt())
            caps.setCapability("usePrebuildWda", true)
            caps.setCapability("appPackage", "") // ToDo: appPackage here
            caps.setCapability("autoGrantPermissions", true)
            caps.setCapability("clearSystemFiles", true)
            caps.setCapability("appium:appInstallStrategy", "ios-deploy")
            driver = IOSDriver(appiumurl, caps)
            iospages = IosPageObjectManager(driver)
        }
        testContext.setAttribute("driver", driver) //custom logger
    }

    @AfterMethod
    fun tearDown(result: ITestResult) {
        logger.info("AfterMethod tearDown")
    }

    @BeforeTest(alwaysRun = true)
    fun beforeTest() {
        logger.info("beforeTest")
    }

    @AfterTest(alwaysRun = true)
    fun afterTest() {
        logger.info("afterTest")
    }

    @AfterSuite
    fun tearDown() {
        driver.quit()
        logger.info("AfterSuite tearDown")
    }

    /**
     * This method is used to capture the screenshot and attach it to the allure report
     */
    private fun captureScreenshot() {
        logger.info("Taking Screenshot")
        Allure.getLifecycle().addAttachment(
            "Screenshot",
            "image/png",
            "png",
            (driver as TakesScreenshot).getScreenshotAs(OutputType.BYTES)
        )
    }

}
