package pol.rubiano.magicapp.app.presentation.error

import pol.rubiano.magicapp.app.domain.ErrorApp

interface ErrorAppUI {
    fun getErrorMessage(): String?
}

class ErrorsAppUI(private val errorApp: ErrorApp) : ErrorAppUI {
    override fun getErrorMessage(): String? = errorApp.message
}