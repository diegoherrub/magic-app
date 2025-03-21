package pol.rubiano.magicapp.app.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.local.MagicAppDataBase
import pol.rubiano.magicapp.app.data.local.daos.CardDao
import pol.rubiano.magicapp.app.data.remote.services.CardService
import retrofit2.Retrofit

@Module
@ComponentScan
class CardModule {

    @Single
    fun provideCardService(retrofit: Retrofit): CardService {
        return retrofit.create(CardService::class.java)
    }

    @Single
    fun provideCardDao(db: MagicAppDataBase): CardDao {
        return db.cardDao()
    }
}