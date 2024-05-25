package android

import io.appium.java_client.AppiumDriver
import io.qameta.allure.Step
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import utilities.ScreenActions
import utilities.`interface`.CommonFunction
import java.time.Duration


open class AndroidScreenActions( driver: AppiumDriver)  : ScreenActions(driver), CommonFunction{
    // TODO: Sample of POM
    // TODO:  screen actions that only works in android

    private val PAGE_TITLE: By = By.id("title")

    /**
     * Scroll until element is found
     * Uses by ANDROID
     *
     * @param findElement id of element that needs to load
     */
    override fun triggerDeeplink(testURL: String, debugapptype: String) {
        driver["<app-package-here>://$testURL"]
    }

}