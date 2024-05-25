package utilities

import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.util.*

data class DeviceEnvironment(
    val platform: String,
    val appName: String,
    val appType: String,
    val automationName: String,
    val version: String,
    val deviceName: String,
    val deviceUdId: String?,
    val orgId: String?,
    val signingId: String?,
    val usePrebuildWda: String?,
    val portWda: String?
)

data class TestEnvironments(
    val TestEnvironments: Map<String, DeviceEnvironment>
)

/**
 * This method is used to read the deviceEnvironment.json file and return the environment
 * @param deviceConfig
 * @return DeviceEnvironment
 */
fun testdata(deviceConfig: String): DeviceEnvironment {
    val jsonString = File(System.getProperty("user.dir") + "/src/main/resources/deviceEnvironment.json").readText()
    val testEnvironments = Gson().fromJson(jsonString, TestEnvironments::class.java)
    val env = testEnvironments.TestEnvironments[deviceConfig]
    if (env == null) {
        System.out.println("Error: The given env is not on the device list configuration list")
        throw IllegalStateException("The given env is not on the device list configuration list. Tests terminated.")
    } else {
        return env
    }
}

/**
 * This method is used to create the environment.properties file for Allure Environment dashboard
 * @param environmentJson
 * @param appversion
 */
fun createEnvironmentFile(environmentJson: DeviceEnvironment?, appversion: String?) {
    val prop = Properties()
    prop.put("platform", environmentJson?.platform)
    prop.put("app.type", environmentJson?.appType)
    prop.put("app.version", appversion)
    prop.put("automation", environmentJson?.automationName)
    prop.put("device.version", environmentJson?.version)
    prop.put("device.name", environmentJson?.deviceName)

    var propertiesFile = System.getProperty("user.dir") + "/environment.properties"
    println("Properties file path: $propertiesFile")
    var fileOutputStream = FileOutputStream(propertiesFile)
    prop.store(fileOutputStream, "Property file for Allure Environment dashboard")
}