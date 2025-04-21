package pol.rubiano.magicapp.app.di

import android.content.Context
import androidx.room.Room
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.cards.data.local.MagicAppDataBase

@Module
@ComponentScan
class LocalModule {

    @Single
    fun provideDataBase(context: Context): MagicAppDataBase {
        return Room.databaseBuilder(
            context,
            MagicAppDataBase::class.java,
            "magicapp-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}