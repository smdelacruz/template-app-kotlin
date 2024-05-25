package utilities

import okhttp3.*
import java.util.*
import okhttp3.OkHttpClient
import okhttp3.Request

object ApiResponseHandler {
    // TODO: Sample of getting API request
    val client = OkHttpClient()
    val getApiProperties: Properties? = ReadEnv.getProperties("api.properties")
    lateinit var request: Request

    fun executeGetRequest(endpoint: String): Response {
        request =  getRequestBuild(
            endpoint = "<endpoint here>",
            url = "<url here>",
            session = "<session here>"
        )
            .addHeader("Host", "<host here>")
            .build()
        return client.newCall(request).execute()
    }

    fun getRequestBuild(
        endpoint: String,
        url: String,
        session: String
    ) : Request.Builder {
        return Request.Builder()
            .url(url + endpoint)
            .addHeader("session", "<session here>")
    }

    fun closeResponse(response: Response?) {
        response?.close()
    }
}
