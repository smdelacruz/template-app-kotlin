package ios

import io.appium.java_client.AppiumDriver

class IosPageObjectManager(driver: AppiumDriver) {
    // TODO: Sample of POM
    val common: IosScreenActions = IosScreenActions(driver)
    val apppage: IosPage = IosPage(driver)
}
