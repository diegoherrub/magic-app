package pol.rubiano.magicapp.features.collections.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pol.rubiano.magicapp.features.collections.domain.CardInCollection

const val COLLECTION_TABLE = "collections"

@Entity(tableName = COLLECTION_TABLE)
@TypeConverters(CollectionEntity.CardInCollectionConverter::class)
class CollectionEntity(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "cards") val cards: List<CardInCollection>
) {
    class CardInCollectionConverter {

        @TypeConverter
        fun fromCardInCollectionList(value: String?): List<CardInCollection> {
            if (value.isNullOrEmpty()) return emptyList()
            val listType = object : TypeToken<List<CardInCollection>>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun toCardInCollectionList(list: List<CardInCollection>?): String? {
            return Gson().toJson(list)
        }
    }
}
