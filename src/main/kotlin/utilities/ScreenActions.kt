package utilities

import io.appium.java_client.AppiumDriver
import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import kotlinx.coroutines.*

open class ScreenActions(val driver: AppiumDriver) {
    // TODO: Sample of POM
    // TODO: Shared screen actions for Android and iOS

    companion object {
        @JvmStatic
        private val WAITING_DURATION_3 = Duration.ofSeconds(3)
    }

    /**
     * Check if the element exist
     *
     * @param element id of text field element
     */
    @Step("Check {element} element exist")
    fun checkElementExist(element: By?): Boolean {
        return try {
            WebDriverWait(driver, WAITING_DURATION_3).until(ExpectedConditions.presenceOfElementLocated(element))
            println("Element exist: $element")
            !driver.findElements(element).isNullOrEmpty()
        } catch (elementDidNotLoad: Exception) {
            println("Element does not exist: $element")
            false
        }
    }

    /**
    * Click an element
    *
    * @param element id of text field element
    */
    @Step("Click {element} element")
    fun click(element: By?) {
        driver.findElement(element)?.click()
    }

}