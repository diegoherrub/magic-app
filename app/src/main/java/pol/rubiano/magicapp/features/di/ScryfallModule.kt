package pol.rubiano.magicapp.features.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.data.remote.ScryfallService
import retrofit2.Retrofit

@Module
@ComponentScan
class ScryfallModule {

    @Single
    fun provideScryfallService(retrofit: Retrofit): ScryfallService {
        return retrofit.create(ScryfallService::class.java)
    }
}