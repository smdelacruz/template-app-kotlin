package utilities

import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException
import io.appium.java_client.service.local.AppiumServiceBuilder
import io.appium.java_client.service.local.flags.GeneralServerFlag
import java.io.File
import java.net.URL
import java.util.*


object ServerAppium {

    // programatically starts appium
    fun start(): URL {
        val service: AppiumDriverLocalService = appiumInstance
        service.clearOutPutStreams()
        try { service.start() } catch (ex: Exception) {
            println("Appium server is not running, attempting start")
        }
        val url = URL("http://127.0.0.1:4723")
//        val url: URL = service.getUrl()
        println("Appium server URL after Appium Server started is: $url")
        return url
    }

    val appiumInstance: AppiumDriverLocalService
        get() {
            val builder: AppiumServiceBuilder
            try {
                val appiumPath = "/usr/local/bin/appium"
                val nodePath = "/usr/local/bin/node"
                val host = "127.0.0.1"
                builder = AppiumServiceBuilder()
//                    .withArgument(ServerArgument { "--use-plugins" }, "images")
//                    .withArgument(ServerArgument { "--port" }, "4723")
                    .usingAnyFreePort()
                    .withIPAddress(host)
                    .usingDriverExecutable(File(nodePath))
                    .withAppiumJS(File(appiumPath))
                    .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                val environment = HashMap<String, String>()
                environment["PATH"] = "/usr/local/bin:" + System.getenv("PATH")
                builder.withEnvironment(environment)
            } catch (e: java.lang.Exception) {
                throw AppiumServerHasNotBeenStartedLocallyException(
                    "Cannot start appium: " + Arrays.toString(
                        e.stackTrace
                    )
                )
            }
            return AppiumDriverLocalService.buildService(builder)
        }

    fun stop() {
        appiumInstance.stop()
        println("Stopped Appium Server.")
    }
}
