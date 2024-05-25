package utilities
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CustomLogger(c: Class<*>) {
    private val logger: Logger = LoggerFactory.getLogger(c)

    fun info(message: String) {
        logger.info(message)
    }

    fun error(message: String, throwable: Throwable? = null) {
        if (throwable != null) {
            logger.error(message, throwable)
        } else {
            logger.error(message)
        }
    }
}
