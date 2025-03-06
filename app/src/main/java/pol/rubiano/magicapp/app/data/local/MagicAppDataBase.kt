package pol.rubiano.magicapp.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pol.rubiano.magicapp.app.data.local.daos.CardDao
import pol.rubiano.magicapp.app.data.local.entities.CardEntity
import pol.rubiano.magicapp.app.data.local.extensions.Converters

@Database(
    entities = [
        CardEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MagicAppDataBase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}