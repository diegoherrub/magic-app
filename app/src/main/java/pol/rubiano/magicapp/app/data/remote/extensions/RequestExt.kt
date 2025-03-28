package pol.rubiano.magicapp.app.data.remote.extensions

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import pol.rubiano.magicapp.app.domain.AppError
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

//suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
//    val response: Response<T>
//    try {
//        response = call.invoke()
//    } catch (exception: Throwable) {
//        return when (exception) {
//            is ConnectException -> Result.failure(AppError.AppConnectionError)
//            is UnknownHostException -> Result.failure(AppError.AppInternetError)
//            is SocketTimeoutException -> Result.failure(AppError.AppInternetError)
//            else -> Result.failure(AppError.AppUnknowError)
//        }
//    }
//    return if (response.isSuccessful && response.body() != null) {
//        Result.success(response.body()!!)
//    } else {
//        Log.d("@pol", "code: ${response.code()}")
//        when (response.code()) {
//            in 500..599 -> Result.failure(AppError.AppServerError)
//            in 400..499 -> Result.failure(AppError.NoResultsError)
//            else -> Result.failure(AppError.AppUnknowError)
//        }
//    }
//
//    val gson = Gson()
//    // Check if the response is successful and has a body
//    if (response.isSuccessful && response.body() != null) {
//        // Convert the response body to JSON and check for an error object.
//        // This assumes that if the response contains an "object": "error" key-value pair,
//        // then it's an error response.
//        val json = gson.toJson(response.body())
//        val jsonObj = try {
//            gson.fromJson(json, JsonObject::class.java)
//        } catch (e: Exception) {
//            null
//        }
//        if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
//            // You can further check details like "code", "status" or "warnings" here.
//            return Result.failure(AppError.NoResultsError)
//        } else {
//            return Result.success(response.body()!!)
//        }
//    } else {
//        // Log the code for debugging purposes
//        Log.d("@pol", "code: ${response.code()}")
//        when (response.code()) {
//            in 500..599 -> return Result.failure(AppError.AppServerError)
//            in 400..499 -> return Result.failure(AppError.NoResultsError)
//            else -> return Result.failure(AppError.AppUnknowError)
//        }
//    }
//}

//suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
//    val response: Response<T>
//    try {
//        response = call.invoke()
//    } catch (exception: Throwable) {
//        return when (exception) {
//            is ConnectException -> Result.failure(AppError.AppConnectionError)
//            is UnknownHostException -> Result.failure(AppError.AppInternetError)
//            is SocketTimeoutException -> Result.failure(AppError.AppInternetError)
//            else -> Result.failure(AppError.AppUnknowError)
//        }
//    }
//
//    val gson = Gson()
//    // If the response is successful, still check if the body indicates an error.
//    if (response.isSuccessful) {
//        val body = response.body()
//        if (body != null) {
//            // Check if the successful body has an error indicator.
//            val json = gson.toJson(body)
//            val jsonObj = try {
//                gson.fromJson(json, JsonObject::class.java)
//            } catch (e: Exception) {
//                null
//            }
//            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
//                return Result.failure(AppError.NoResultsError)
//            }
//            return Result.success(body)
//        } else {
//            return Result.failure(AppError.AppUnknowError)
//        }
//    } else {
//        // For unsuccessful responses, read from errorBody.
//        val errorBodyString = response.errorBody()?.string()
//        if (!errorBodyString.isNullOrEmpty()) {
//            val jsonObj = try {
//                gson.fromJson(errorBodyString, JsonObject::class.java)
//            } catch (e: Exception) {
//                null
//            }
//            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
//                // Log the code for debugging purposes
//                Log.d("@pol", "Error response code: ${response.code()} - parsed error from errorBody")
//                return Result.failure(AppError.NoResultsError)
//            }
//        }
//        Log.d("@pol", "Error response code: ${response.code()}")
//        return when (response.code()) {
//            in 500..599 -> Result.failure(AppError.AppServerError)
//            in 400..499 -> Result.failure(AppError.NoResultsError)
//            else -> Result.failure(AppError.AppUnknowError)
//        }
//    }
//}


//suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
//    val response: Response<T>
//    try {
//        response = call.invoke()
//    } catch (exception: Throwable) {
//        return when (exception) {
//            is ConnectException -> Result.failure(AppError.AppConnectionError)
//            is UnknownHostException -> Result.failure(AppError.AppInternetError)
//            is SocketTimeoutException -> Result.failure(AppError.AppInternetError)
//            else -> Result.failure(AppError.AppUnknowError)
//        }
//    }
//
//    val gson = Gson()
//    // If response is successful, still check if the body indicates an error.
//    if (response.isSuccessful) {
//        val body = response.body()
//        if (body != null) {
//            val json = gson.toJson(body)
//            val jsonObj = try {
//                gson.fromJson(json, JsonObject::class.java)
//            } catch (e: Exception) {
//                null
//            }
//            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
//                Log.d("@pol", "Successful response but error indicator found in body: $json")
//                return Result.failure(AppError.NoResultsError)
//            }
//            return Result.success(body)
//        } else {
//            Log.d("@pol", "Successful response but body is null")
//            return Result.failure(AppError.AppUnknowError)
//        }
//    } else {
//        // For unsuccessful responses, read from errorBody.
//        val errorBodyString = response.errorBody()?.string()
//        Log.d("@pol", "Response code: ${response.code()} Error body: $errorBodyString")
//        if (!errorBodyString.isNullOrEmpty()) {
//            val jsonObj = try {
//                gson.fromJson(errorBodyString, JsonObject::class.java)
//            } catch (e: Exception) {
//                Log.d("@pol", "Error parsing error body: ${e.message}")
//                null
//            }
//            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
//                Log.d("@pol", "Error indicator found in error body")
//                return Result.failure(AppError.NoResultsError)
//            }
//        }
//        return when (response.code()) {
//            in 500..599 -> Result.failure(AppError.AppServerError)
//            in 400..499 -> Result.failure(AppError.NoResultsError)
//            else -> Result.failure(AppError.AppUnknowError)
//        }
//    }
//}

suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
    val response: Response<T>
    try {
        response = call.invoke()
    } catch (exception: Throwable) {
        Log.e("@pol", "Exception during API call: ${exception.message}")
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
                Log.e("@pol", "Error parsing successful body: ${e.message}")
                null
            }
            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
                Log.e("@pol", "Successful response but error indicator found in body: $json")
                return Result.failure(AppError.NoResultsError)
            }
            return Result.success(body)
        } else {
            Log.e("@pol", "Successful response but body is null")
            return Result.failure(AppError.AppUnknowError)
        }
    } else {
        val errorBodyString = response.errorBody()?.string()
        Log.e("@pol", "Non-successful response. Code: ${response.code()}, Error body: $errorBodyString")
        if (!errorBodyString.isNullOrEmpty()) {
            val jsonObj = try {
                gson.fromJson(errorBodyString, JsonObject::class.java)
            } catch (e: Exception) {
                Log.e("@pol", "Error parsing error body: ${e.message}")
                null
            }
            if (jsonObj != null && jsonObj.has("object") && jsonObj.get("object").asString == "error") {
                Log.e("@pol", "Error indicator found in error body.")
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
