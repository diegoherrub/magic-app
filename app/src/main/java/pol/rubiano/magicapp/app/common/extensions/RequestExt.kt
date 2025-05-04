package pol.rubiano.magicapp.app.common.extensions

import com.google.gson.Gson
import com.google.gson.JsonObject
import pol.rubiano.magicapp.app.domain.AppError
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
    val response: Response<T>
    try {
        response = call.invoke()
    } catch (exception: Throwable) {
        return when (exception) {
            is ConnectException -> Result.failure(AppError.AppConnectionError)
            is UnknownHostException -> Result.failure(AppError.AppInternetError)
            is SocketTimeoutException -> Result.failure(AppError.AppInternetError)
            else -> Result.failure(AppError.AppUnknowError)
        }
    }

    val gson = Gson()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            val json = gson.toJson(body)
            val jsonObj = try {
                gson.fromJson(json, JsonObject::class.java)
            } catch (e: Exception) {
                null
            }
            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
                return Result.failure(AppError.NoResultsError)
            }
            return Result.success(body)
        } else {
            return Result.failure(AppError.AppUnknowError)
        }
    } else {
        val errorBodyString = response.errorBody()?.string()
        if (!errorBodyString.isNullOrEmpty()) {
            val jsonObj = try {
                gson.fromJson(errorBodyString, JsonObject::class.java)
            } catch (e: Exception) {
                null
            }
            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
                return Result.failure(AppError.NoResultsError)
            }
        }
        return when (response.code()) {
            in 500..599 -> Result.failure(AppError.AppServerError)
            in 400..499 -> Result.failure(AppError.NoResultsError)
            else -> Result.failure(AppError.AppUnknowError)
        }
    }
}
