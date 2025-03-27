package pol.rubiano.magicapp.app.data.remote.extensions

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
    return if (response.isSuccessful && response.body() != null) {
        Result.success(response.body()!!)
    } else {
        when (response.code()) {
            in 500..599 -> Result.failure(AppError.AppServerError)
            in 400..499 -> Result.failure(AppError.AppDataError)
            else -> Result.failure(AppError.AppUnknowError)
        }
    }
}