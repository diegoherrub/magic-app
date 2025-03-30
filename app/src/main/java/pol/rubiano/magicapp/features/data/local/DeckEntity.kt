package pol.rubiano.magicapp.features.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pol.rubiano.magicapp.app.data.local.extensions.Converters

const val DECKS_TABLE = "decks"
const val DECK_ID = "id"


@Entity(tableName = DECKS_TABLE)
@TypeConverters(Converters::class)
class DeckEntity(
    @PrimaryKey @ColumnInfo(name = DECK_ID) val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "card_ids") val cardIds: List<String>,
    @ColumnInfo(name = "side_board") val sideBoard: List<String>,
    @ColumnInfo(name = "maybe_board") val maybeBoard: List<String>
)
