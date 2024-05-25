package utilities.utilities

import java.util.Properties
import kotlin.jvm.JvmStatic
import java.io.*


object ReadEnv {
    @JvmStatic
    fun getProperties(propertyFileName: String?): Properties? {
        val input = ReadEnv::class.java.classLoader.getResourceAsStream(propertyFileName)
        val prop = Properties()
        if (input == null) {
            println("Unable to find the properties file: $propertyFileName.")
            return null
        }
        try {
            prop.load(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return prop
    }
}