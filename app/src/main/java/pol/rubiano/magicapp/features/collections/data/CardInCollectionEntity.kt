package pol.rubiano.magicapp.features.collections.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import pol.rubiano.magicapp.app.data.local.CardEntity

const val CARDS_IN_COLLECTION_TABLE = "cards_in_collection"

@Entity(
    tableName = CARDS_IN_COLLECTION_TABLE,
    primaryKeys = ["card_id", "collection_name"],
    foreignKeys = [
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["card_id"]
        ),
        ForeignKey(
            entity = CollectionEntity::class,
            parentColumns = ["name"],
            childColumns = ["collection_name"]
        )
    ]
)
class CardInCollectionEntity(
    @ColumnInfo(name = "card_id") var cardId: String,
    @ColumnInfo(name = "collection_name") var collectionName: String,
    @ColumnInfo(name = "copies") var copies: Int
)