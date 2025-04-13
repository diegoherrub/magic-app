package pol.rubiano.magicapp.features.collections.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.local.MagicAppDataBase
import pol.rubiano.magicapp.features.collections.data.CollectionDao

@Module
@ComponentScan("pol.rubiano.magicapp.features.collections.data")
class CollectionModule {
    @Single
    fun provideCollectionDao(db: MagicAppDataBase): CollectionDao {
        return db.collectionDao()
    }
}