package pol.rubiano.magicapp.app.data.remote.extensions

import pol.rubiano.magicapp.app.domain.ErrorApp
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
            is ConnectException -> Result.failure(ErrorApp.ConnectionErrorApp)
            is UnknownHostException -> Result.failure(ErrorApp.InternetErrorApp)
            is SocketTimeoutException -> Result.failure(ErrorApp.InternetErrorApp)
            else -> Result.failure(ErrorApp.UnknowErrorApp)
        }
    }
    return if (response.isSuccessful && response.body() != null) {
        Result.success(response.body()!!)
    } else {
        when (response.code()) {
            in 500..599 -> Result.failure(ErrorApp.ServerErrorApp)
            in 400..499 -> Result.failure(ErrorApp.DataErrorApp)
            else -> Result.failure(ErrorApp.UnknowErrorApp)
        }
    }
}