package pol.rubiano.magicapp.app.presentation.error

import org.koin.core.annotation.Factory
import pol.rubiano.magicapp.app.domain.AppError

@Factory
class AppErrorUIFactory {
    fun build(appError: AppError): AppErrorUI {
        return ErrorsAppUI(appError)
    }
}