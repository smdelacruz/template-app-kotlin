package android

import io.appium.java_client.AppiumDriver

class AndroidPageObjectManager(driver: AppiumDriver) {
    // TODO: Sample of POM
    val common: AndroidScreenActions = AndroidScreenActions(driver)
    val apppage: AndroidPage = AndroidPage(driver)
}
