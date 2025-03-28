package pol.rubiano.magicapp.app.domain

sealed class AppError : Throwable() {

    object AppConnectionError : AppError() {
        private fun readResolve(): Any = AppConnectionError
    }

    object AppInternetError : AppError() {
        private fun readResolve(): Any = AppInternetError
    }

    object AppServerError : AppError() {
        private fun readResolve(): Any = AppServerError
    }

    object AppDataError : AppError() {
        private fun readResolve(): Any = AppDataError
    }

    object NoResultsError : AppError() {
        private fun readResolve(): Any = NoResultsError
    }

    object AppUnknowError : AppError() {
        private fun readResolve(): Any = AppUnknowError
    }
}