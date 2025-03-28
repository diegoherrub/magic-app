package pol.rubiano.magicapp.app.presentation.error

import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.AppError
import java.util.Locale

interface AppErrorUI {
    fun getErrorImage(): Int
}

class ErrorsAppUI(private val appError: AppError) : AppErrorUI {
    override fun getErrorImage(): Int {
        val locale = Locale.getDefault().language
        return when (appError) {
            is AppError.AppConnectionError -> if (locale == "es") R.drawable.error_de_conexion else R.drawable.connection_error
            is AppError.AppInternetError -> if (locale == "es") R.drawable.error_de_internet else R.drawable.internet_error
            is AppError.AppServerError -> if (locale == "es") R.drawable.error_de_servidor else R.drawable.server_error
            is AppError.NoResultsError -> if (locale == "es") R.drawable.escrutinio_vacio else R.drawable.empty_scry
            is AppError.AppDataError -> if (locale == "es") R.drawable.error_de_datos else R.drawable.data_error
            is AppError.AppUnknowError -> if (locale == "es") R.drawable.error_desconocido else R.drawable.unknown_error
        }
    }
}