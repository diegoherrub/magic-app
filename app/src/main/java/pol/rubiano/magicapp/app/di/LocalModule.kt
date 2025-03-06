package pol.rubiano.magicapp.app.di

import android.content.Context
import androidx.room.Room
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.local.MagicAppDataBase

@Module
@ComponentScan
class LocalModule {

    @Single
    fun provideDataBase(context: Context): MagicAppDataBase {
        val database = Room.databaseBuilder(
            context,
            MagicAppDataBase::class.java,
            "magicapp-db"
        )
        database.fallbackToDestructiveMigration()
        return database.build()
    }
}