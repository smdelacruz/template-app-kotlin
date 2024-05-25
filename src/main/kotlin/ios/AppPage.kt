package ios

import io.appium.java_client.AppiumBy
import io.appium.java_client.AppiumDriver
import io.qameta.allure.Description
import io.qameta.allure.Step
import org.openqa.selenium.By
import org.testng.annotations.DataProvider
import utilities.AppCenter


open class IosPage(driver: AppiumDriver?) : IosScreenActions(driver!!) {
    // TODO: Sample of POM
    companion object{
        private val ELEMENT_NAME: By = AppiumBy.name("<qa-id-here>")

        /**
         * DataProvider
         */
        @JvmStatic
        @DataProvider(name = "dataprovider")
        fun dataprovider(): Array<By> {
            return arrayOf(
                //ELEMENT_NAMES here , e.g. ELEMENT_NAME
            )
        }
    }
    @Step("Steps to do here")
    fun stepsToDoHere() {
        click(ELEMENT_NAME)
    }
}