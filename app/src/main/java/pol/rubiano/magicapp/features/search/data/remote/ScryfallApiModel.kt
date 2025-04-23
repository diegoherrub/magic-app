package pol.rubiano.magicapp.features.search.data.remote

import com.google.gson.annotations.SerializedName
import pol.rubiano.magicapp.features.cards.data.remote.CardApiModel

data class ScryfallApiModel(
    @SerializedName("data") val data: List<CardApiModel>? = null,
    @SerializedName("has_more") val hasMore: Boolean?,
    @SerializedName("next_page") val nextPage: String?,
    @SerializedName("total_cards") val totalCards: Int?,
)
