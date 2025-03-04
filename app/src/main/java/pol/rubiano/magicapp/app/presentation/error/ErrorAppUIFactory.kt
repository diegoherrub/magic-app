package pol.rubiano.magicapp.app.presentation.error

import org.koin.core.annotation.Factory
import pol.rubiano.magicapp.app.domain.ErrorApp

@Factory
class ErrorAppUIFactory {
    fun build(errorApp: ErrorApp): ErrorAppUI {
        return ErrorsAppUI(errorApp)
    }
}