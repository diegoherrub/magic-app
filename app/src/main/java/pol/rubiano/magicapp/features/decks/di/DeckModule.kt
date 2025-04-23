package pol.rubiano.magicapp.features.decks.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.local.MagicAppDataBase
import pol.rubiano.magicapp.features.decks.data.local.DeckDao

@Module
@ComponentScan("pol.rubiano.magicapp.features.data.local")
class DeckModule {
    @Single
    fun provideDeckDao(db: MagicAppDataBase): DeckDao {
        return db.deckDao()
    }
}