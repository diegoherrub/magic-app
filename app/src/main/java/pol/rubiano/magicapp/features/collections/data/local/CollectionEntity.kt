package pol.rubiano.magicapp.features.collections.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection

const val COLLECTION_TABLE = "collections"

@Entity(tableName = COLLECTION_TABLE)
class CollectionEntity(
    @PrimaryKey @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "cards") val cards: List<CardInCollection>
)