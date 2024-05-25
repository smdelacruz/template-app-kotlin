package ios

import io.appium.java_client.AppiumBy
import io.appium.java_client.AppiumDriver
import utilities.ScreenActions
import utilities.`interface`.CommonFunction


open class IosScreenActions(driver: AppiumDriver): ScreenActions(driver), CommonFunction {
    // TODO: Sample of POM
    // TODO:  screen actions that only works in iOS

    private val ELEMENT_NAME = AppiumBy.accessibilityId("<element id here>")

    /**
     * Common function - Trigger deeplink
     */
    override fun triggerDeeplink(testURL: String, debugapptype: String) {
        driver["<app-package-here>://$testURL"]
    }


}
