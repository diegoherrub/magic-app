package pol.rubiano.magicapp.app.cards.data.remote

import com.google.gson.annotations.SerializedName

data class CardApiModel(
    @SerializedName("id") val id: String,
    @SerializedName("oracle_id") val oracleId: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("type_line") val typeLine: String?,
    @SerializedName("rarity") val rarity: String?,
    @SerializedName("oracle_text") val oracleText: String?,
    @SerializedName("loyalty") val loyalty: String?,
    @SerializedName("power") val power: String?,
    @SerializedName("toughness") val toughness: String?,
    @SerializedName("cmc") val cmc: Double?,
    @SerializedName("mana_cost") val manaCost: String?,
    @SerializedName("produced_mana") val producedMana: List<String>?,
    @SerializedName("released_at") val releasedAt: String?,
    @SerializedName("image_uris") val imageUris: ImageUris?,
    @SerializedName("set") val set: String?,
    @SerializedName("set_name") val setName: String?,
    @SerializedName("set_type") val setType: String?,
    @SerializedName("collector_number") val collectorNumber: String?,
    @SerializedName("card_faces") val cardFaces: List<CardFace>?,
    @SerializedName("legalities") val legalities: Legalities
) {

    data class ImageUris(
        @SerializedName("small") val imageSmall: String?,
        @SerializedName("border_crop") val imageCrop: String?
    )

    data class CardFace(
        @SerializedName("name") val name: String?,
        @SerializedName("type_line") val typeLine: String?,
        @SerializedName("oracle_text") val oracleText: String?,
        @SerializedName("power") val power: String?,
        @SerializedName("toughness") val toughness: String?,
        @SerializedName("loyalty") val loyalty: String?,
        @SerializedName("artist") val artist: String?,
        @SerializedName("image_uris") val imageUris: ImageUris?,
        @SerializedName("cmc") val cmc: Double?,
        @SerializedName("mana_cost") val manaCost: String?,
        @SerializedName("produced_mana") val producedMana: List<String>?
    )

    data class Legalities(
        @SerializedName("standard") val standard: String?,
        @SerializedName("future") val future: String?,
        @SerializedName("historic") val historic: String?,
        @SerializedName("timeless") val timeless: String?,
        @SerializedName("gladiator") val gladiator: String?,
        @SerializedName("pioneer") val pioneer: String?,
        @SerializedName("explorer") val explorer: String?,
        @SerializedName("modern") val modern: String?,
        @SerializedName("legacy") val legacy: String?,
        @SerializedName("pauper") val pauper: String?,
        @SerializedName("vintage") val vintage: String?,
        @SerializedName("penny") val penny: String?,
        @SerializedName("commander") val commander: String?,
        @SerializedName("oathbreaker") val oathbreaker: String?,
        @SerializedName("standardbrawl") val standardBrawl: String?,
        @SerializedName("brawl") val brawl: String?,
        @SerializedName("alchemy") val alchemy: String?,
        @SerializedName("paupercommander") val pauperCommander: String?,
        @SerializedName("duel") val duel: String?,
        @SerializedName("oldschool") val oldschool: String?,
        @SerializedName("premodern") val premodern: String?,
        @SerializedName("predh") val predh: String?,
    )
}