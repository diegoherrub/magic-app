package pol.rubiano.magicapp.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pol.rubiano.magicapp.app.data.local.extensions.Converters
import pol.rubiano.magicapp.features.data.local.DeckDao
import pol.rubiano.magicapp.features.data.local.DeckEntity

@Database(
    entities = [
        CardEntity::class,
        DeckEntity::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MagicAppDataBase : RoomDatabase() {
    abstract fun cardDao(): CardDao
    abstract fun deckDao(): DeckDao
}