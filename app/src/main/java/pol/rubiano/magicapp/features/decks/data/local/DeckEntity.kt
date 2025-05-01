package pol.rubiano.magicapp.features.decks.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pol.rubiano.magicapp.app.data.local.Converters
import pol.rubiano.magicapp.features.collections.domain.models.CardInCollection
import pol.rubiano.magicapp.features.decks.domain.models.CardInDeck

const val DECKS_TABLE = "decks"
const val DECK_ID = "id"


@Entity(tableName = DECKS_TABLE)
class DeckEntity(
    @PrimaryKey @ColumnInfo(name = DECK_ID) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "colors") val colors: List<String>,
    @ColumnInfo(name = "cards") val cards: List<CardInDeck>
)
