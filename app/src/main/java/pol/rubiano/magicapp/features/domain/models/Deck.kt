package pol.rubiano.magicapp.features.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Deck(
    val id: String,
    val name: String,
    val description: String,
    val colors: List<String> = emptyList(),
    val cardIds: List<String> = emptyList(),
    val sideBoard: List<String> = emptyList(),
    val maybeBoard: List<String> = emptyList()
) : Parcelable
