package ios

import TestBase
import io.qameta.allure.Description
import org.openqa.selenium.By
import org.testng.Assert
import org.testng.annotations.*

/**
 * Test cases from Documentation
 * documentation/TestCase.md
 */
class TestCaseName  : TestBase() {

    @Test()
    @Description("TC01: [Test case name]")
    fun TC01_TestCaseName(){
        // Steps here and assertions
        iospages.apppage.stepsToDoHere()
        Assert.assertTrue(true)
    }

}
