package pol.rubiano.magicapp.app.cards.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pol.rubiano.magicapp.features.cards.data.local.CardDao
import pol.rubiano.magicapp.features.collections.data.CardInCollectionEntity
import pol.rubiano.magicapp.features.collections.data.CollectionDao
import pol.rubiano.magicapp.features.collections.data.CollectionEntity
import pol.rubiano.magicapp.features.data.local.DeckDao
import pol.rubiano.magicapp.features.data.local.DeckEntity

@Database(
    entities = [
        CardEntity::class,
        DeckEntity::class,
        CollectionEntity::class,
        CardInCollectionEntity::class,
    ],
    version = 11,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MagicAppDataBase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun deckDao(): DeckDao
    abstract fun collectionDao(): CollectionDao
}