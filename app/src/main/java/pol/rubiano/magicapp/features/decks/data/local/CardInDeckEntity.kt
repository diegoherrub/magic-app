package pol.rubiano.magicapp.features.decks.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import pol.rubiano.magicapp.features.cards.data.local.CardEntity

const val CARDS_IN_DECK_TABLE = "cards_in_deck"

@Entity(
    tableName = CARDS_IN_DECK_TABLE,
    primaryKeys = ["card_id", "deck_id"],
    foreignKeys = [
        ForeignKey(
            entity = CardEntity::class,
            parentColumns = ["id"],
            childColumns = ["card_id"]
        ),
        ForeignKey(
            entity = DeckEntity::class,
            parentColumns = ["id"],
            childColumns = ["deck_id"]
        )
    ],
    indices = [Index(value = ["deck_id"])]
)
class CardInDeckEntity(
    @ColumnInfo(name = "card_id") var cardId: String,
    @ColumnInfo(name = "deck_id") var deckId: String,
    @ColumnInfo(name = "main_board_copies") var mainBoardCopies: Int,
    @ColumnInfo(name = "side_board_copies") var sideBoardCopies: Int,
    @ColumnInfo(name = "may_be_board_copies") var mayBeBoardCopies: Int,
)