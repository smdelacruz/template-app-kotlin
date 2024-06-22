package utilities

import com.beust.jcommander.internal.Lists
import io.appium.java_client.AppiumDriver
import io.qameta.allure.Step
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import org.openqa.selenium.WebElement as WebElement1
import kotlinx.coroutines.*

open class ScreenActions(val driver: AppiumDriver) {
    // TODO: Shared screen actions for Android and iOS


    companion object {
        @JvmStatic
        private val WAITING_DURATION_3 = Duration.ofSeconds(3)
        private val WAITING_DURATION_5 = Duration.ofSeconds(5)
        private val WAITING_DURATION_7 = Duration.ofSeconds(7)
        private val WAITING_DURATION_10 = Duration.ofSeconds(10)
    }


    /**
     * Deeplink
     */
    open fun deeplink(args: Map<String, Any>) {
        driver.executeScript("mobile: deepLink", args)
    }

    /**
     *  retrieve the elementId of an element identified by a locator using the WebElement
     */
    open fun getElementId(element: By) : String{
        // Find the element using the locator
        val element1: WebElement1? = driver.findElement(element)

        // Get the elementId
        val elementId: String? = element1?.getAttribute("resourceId")
        return elementId.toString()

    }

    /**
     * Swipe
     */
    open fun swipe(fromX: Int, fromY: Int, offsetX: Int, offsetY: Int) {
        val args: MutableMap<String, Any> = HashMap()
        args["command"] = "input"
        args["args"] = Lists.newArrayList("swipe", fromX, fromY, offsetX, offsetY)
        driver.executeScript("mobile: shell", args)
    }


    /**
     * Check if the element exist
     *
     * @param element id of text field element
     */
    @Step("Check {element} element exist")
    fun checkElementExist(element: By?): Boolean {
        return try {
            WebDriverWait(driver, WAITING_DURATION_10).until(ExpectedConditions.presenceOfElementLocated(element))
            println("Element exist: $element")
            !driver.findElements(element).isNullOrEmpty()
        } catch (elementDidNotLoad: Exception) {
            println("Element does not exist: $element")
            false
        }
    }

    /**
     * Check if an element exist
     *
     * @param element id of element that needs to load
     * @throws Exception
     */
    open fun checkElementVisibility(element: By): Boolean {
        return try {
            val driverWait = WebDriverWait(driver, WAITING_DURATION_3)
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(element))
            println("Element is visible: $element")
            driver.findElements(element).size > 0
        } catch (elementDidNotLoad: java.lang.Exception) {
            println("Element is not visible: $element")
            false
        }
    }

    /**
     * Wait for the element to be visible and try for 3x
     *
     * @param element id of text field element
     */
    @Step("Wait {element} element visibility")
    fun waitForVisibility(element: By?) {
        for (i in 1..3) {
            try {
                WebDriverWait(driver, WAITING_DURATION_3).until(ExpectedConditions.visibilityOfElementLocated(element))
                return
            } catch (elementDidNotLoad: Exception) {
                println(String.format("Element '%s' is not visible load after", element))
            }
        }
    }

    /**
     * Wait for the precense of the element and try for 3x
     *
     * @param element id of text field element
     */
    @Step("Wait {element} element id to load")
    fun waitForElementToLoadByID(element: By?) {
        for (i in 1..3) {
            try {
                WebDriverWait(driver, WAITING_DURATION_7).until(ExpectedConditions.presenceOfElementLocated(element))
                return
            } catch (elementDidNotLoad: Exception) {
                println(String.format("Element '%s' is not visible load after", element))
            }
        }
    }

    /**
     * Wait for the given element to be clickable and try for 3x
     *
     * @param element id of text field element
     */
    @Step("Wait {element} element to be clickable")
    fun waitForElementToBeClickable(element: By?) {
        for (i in 1..3) {
            try {
                WebDriverWait(driver, WAITING_DURATION_5).until(ExpectedConditions.elementToBeClickable(element))
                return
            } catch (elementDidNotLoad: Exception) {
                println(String.format("Element '%s' is not visible load after", element))
            }
        }
    }

    /**
     * Click an element
     *
     * @param element id of text field element
     */
    @Step("Click {element} element")
    fun click(element: By?) {
        var flag = false
        Thread.sleep(2000)
        try {
            waitForElementToBeClickable(element)
            driver.findElement(element)?.click()
            flag = true
        } catch (e: Exception) {
            println("Error: Unable to click on \"$element\"")
        } finally {
            if (flag) {
                println("Able to click on \"$element\"")
            } else {
                println("Unable to click on \"$element\"")
            }
        }
    }

    /**
     * Input on text field
     *
     * @param element id of text field element
     * @param input String to input on text field
     */
    @Step("Input text {1} on element {0}")
    fun inputText(element: By, input: String?) {
        driver.findElement(element)?.click()
        driver.findElement(element)?.clear()
        driver.findElement(element)?.sendKeys(input)
    }

    /**
     * Clear a text field based on the element
     *
     * @param element id of text field element
     */
    @Step("Clear text field on element {0}")
    fun clearText(element: By) {
        driver.findElement(element)?.clear()
        driver.findElement(element)?.sendKeys("\n")
    }

    /**
     * [Enter] on Keyboard
     *
     * @param element id of text field element
     */
    @Step("Enter on Keyboard")
    fun keyboardEnter(element: By) {
        driver.findElement(element)?.sendKeys("\n")
    }

    /**
     * Get text from element
     *
     * @param element id of element that needs to load
     * @throws Exception
     */
    @Step("Get element text from element {0}")
    @Throws(java.lang.Exception::class)
    open fun getElementText(element: By?): String {
        val wait = WebDriverWait(driver, WAITING_DURATION_5)
        wait.until(ExpectedConditions.presenceOfElementLocated(element))
        return driver.findElement(element).getText()
    }


    /**
     * Check if text is visible on the screen
     *
     * @param findText id of element that needs to load
     * @throws Exception
     */
    @Step("Find if the text is on the page source (XML)")
    @Throws(java.lang.Exception::class)
    open fun isTextVisible(findText: String) : Boolean{
        return driver.pageSource.contains(findText)
    }

    /**
     * Get {element} value
     *
     * @param element id of element that needs to be checked
     * @throws Exception
     */
    @Step("Check if {element} is checked")
    @Throws(java.lang.Exception::class)
    open fun getElementValue(element: By?) : String{
        return driver.findElement(element).getAttribute("value").toString()
    }

    /**
     * Get text from element
     *
     * @param element id of element that needs to load
     * @throws Exception
     */
    @Step("Extract number from text")
    @Throws(java.lang.Exception::class)
    open fun extractNumber(elementText: String): Int {
        return elementText.replace(Regex("[^0-9]"), "").toInt()
    }

    /**
     * Get {element} value
     *
     * @param element id of element that needs to be checked
     * @throws Exception
     */
    @Step("Check if {element} is checked")
    @Throws(java.lang.Exception::class)
    fun isEnabled(element: By?) : Boolean{
        return driver.findElement(element).isEnabled
    }


    /**
     * Check if {element} is checked
     *
     * @param element id of element that needs to be checked
     * @throws Exception
     */
    @Step("Check if {element} is checked")
    @Throws(java.lang.Exception::class)
    open fun isSelected(element: By?) : Boolean{
        return driver.findElement(element).isSelected
    }

    /**
     * Check if {element} is checked
     *
     * @param element id of element that needs to be checked
     * @throws Exception
     */
    @Step("Check if {element} is checked")
    @Throws(java.lang.Exception::class)
    open fun isChecked(element: By?) : Boolean{
        return driver.findElement(element).getAttribute("checked").toBoolean()
    }

    /**
     * Find all similar {element} and click the first element
     *
     * @param element id of element that needs to load
     * @throws Exception
     */
    @Step("Click and scroll until element is visible")
    @Throws(java.lang.Exception::class)
    open fun findElements(element: By) : Int{
        val elements = driver.findElements(element)
        return elements.size
    }

    /**
     * Find all similar {element} and click the first element
     *
     * @param element id of element that needs to load
     * @throws Exception
     */
    @Step("Click and scroll until element is visible")
    @Throws(java.lang.Exception::class)
    open fun  findFirstElementAndClick(element: By) {
        val elements = driver.findElements(element)
        if (elements.isNotEmpty()) {
            elements[0].click()
        }
    }


    /**
     * Scroll Down
     * Used by iOS
     */
    open fun scrollDown() {
        val params: MutableMap<String, Any> = java.util.HashMap()
        params["direction"] = "down"
        params["velocity"] = 0.1
        driver.executeScript("mobile: scroll", params)
    }

    /**
     * Scroll Up
     * Used by iOS
     */
    open fun scrollUp() {
        val params: MutableMap<String, Any> = java.util.HashMap()
        params["direction"] = "up"
        params["velocity"] = 2
        driver.executeScript("mobile: scroll", params)
    }

    /**
     * Scroll until element is found
     * Uses by IOS
     *
     * @param element id of element that needs to load
     */
    fun scrollUntilElement(element: By, direction: String ?= "") {
        while (!checkElementVisibility(element)) {
            if (direction == "UP"){
                scrollUp()
            }
            else{
                scrollDown()
            }
        }
    }

    /**
     * Scroll until element is found and click on the element
     * Uses by IOS
     *
     * @param element id of element that needs to load
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    open fun clickAndScrollUntilElement(element: By) {
        var elementIsClickable: Boolean = checkElementVisibility(element)
        while (!elementIsClickable) {
            scrollUntilElement(element)
            elementIsClickable = checkElementVisibility(element)
        }
        click(element)
    }

    /**
     * Check if Keyboard is shown
     */
    open fun isKeyboardPresent(): Boolean  {
        return driver.executeScript("mobile: isKeyboardShown").toString().equals("true");
    }
}