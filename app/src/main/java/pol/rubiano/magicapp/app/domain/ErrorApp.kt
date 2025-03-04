package pol.rubiano.magicapp.app.domain

sealed class ErrorApp : Throwable() {

    object ConnectionErrorApp : ErrorApp() {
        private fun readResolve(): Any = ConnectionErrorApp
    }

    object InternetErrorApp : ErrorApp() {
        private fun readResolve(): Any = InternetErrorApp
    }

    object ServerErrorApp : ErrorApp() {
        private fun readResolve(): Any = ServerErrorApp
    }

    object DataErrorApp : ErrorApp() {
        private fun readResolve(): Any = DataErrorApp
    }

    object UnknowErrorApp : ErrorApp() {
        private fun readResolve(): Any = UnknowErrorApp
    }
}