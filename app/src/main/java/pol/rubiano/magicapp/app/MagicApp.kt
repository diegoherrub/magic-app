package pol.rubiano.magicapp.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module
import pol.rubiano.magicapp.app.di.AppModule
import pol.rubiano.magicapp.app.di.CardModule
import pol.rubiano.magicapp.app.di.LocalModule
import pol.rubiano.magicapp.app.di.RemoteModule

class MagicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MagicApp)
            modules(
                AppModule().module,
                LocalModule().module,
                RemoteModule().module,
                CardModule().module
            )
        }
    }
}