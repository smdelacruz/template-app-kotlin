import android.AndroidPageObjectManager
import io.appium.java_client.AppiumDriver
import io.qameta.allure.Allure
import ios.IosPageObjectManager
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.annotations.*
import utilities.*

open class TestBase {
    lateinit var driver: AppiumDriver
    lateinit var iospages: IosPageObjectManager
    lateinit var androidpages: AndroidPageObjectManager
    private val logger = CustomLogger(this::class.java)     // Initialize your custom logger
    val testdevice = testdata(System.getProperty("testDevice"))
    private val appcenter = // access app here

    @BeforeSuite
    fun setUp(testContext: ITestContext) {
        // ToDo: Automation Starts here
        /**val appiumurl = ServerAppium.start()

        // Based on the parameter when test was run, we checked with os to run on deviceEnvironment.json
        if (os == OS.ANDROID.toString()) {
            caps.setCapability("caps", "<caps here>")
            driver = AndroidDriver(appiumurl, caps)
            androidpages = AndroidPageObjectManager(driver)
        } else {
            caps.setCapability("caps", "<caps here>")
            driver = IOSDriver(appiumurl, caps)
            iospages = IosPageObjectManager(driver)
        }
        testContext.setAttribute("driver", driver) //custom logger
         **/
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
