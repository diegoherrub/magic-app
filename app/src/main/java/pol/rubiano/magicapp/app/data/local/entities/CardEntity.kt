package pol.rubiano.magicapp.app.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import pol.rubiano.magicapp.app.data.local.extensions.Converters
import pol.rubiano.magicapp.app.domain.entities.Card
import java.util.Date

const val CARDS_TABLE = "cards"
const val CARD_ID = "id"

@Entity(tableName = CARDS_TABLE)
class CardEntity (
    @PrimaryKey @ColumnInfo(name = CARD_ID) val id: String,
    @ColumnInfo(name = "oracle_id") val oracleId: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "type_line") val typeLine: String?,
    @ColumnInfo(name = "rarity") val rarity: String?,
    @ColumnInfo(name = "oracle_text") val oracleText: String?,
    @ColumnInfo(name = "loyalty") val loyalty: String?,
    @ColumnInfo(name = "power") val power: String?,
    @ColumnInfo(name = "toughness") val toughness: String?,
    @ColumnInfo(name = "cmc") val cmc: Double?,
    @ColumnInfo(name = "mana_cost") val manaCost: String?,
    @ColumnInfo(name = "produced_mana") val producedMana: List<String>?,
    @ColumnInfo(name = "released_at") val releasedAt: Date?,
    @ColumnInfo(name = "small") val imageSmall: String?,
    @ColumnInfo(name = "normal") val imageNormal: String?,
    @ColumnInfo(name = "set") val set: String?,
    @ColumnInfo(name = "set_name") val setName: String?,
    @ColumnInfo(name = "set_type") val setType: String?,
    @ColumnInfo(name = "collector_number") val collectorNumber: String?,
    @Embedded(prefix = "front_") @TypeConverters(Converters::class) val frontFace: Card.Face?,
    @Embedded(prefix = "back") @TypeConverters(Converters::class) val backFace: Card.Face?,
    @Embedded(prefix = "leg_") @TypeConverters(Converters::class) val legalities: Card.Legalities?,
)