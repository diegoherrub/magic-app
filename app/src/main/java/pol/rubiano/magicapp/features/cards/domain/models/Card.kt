package pol.rubiano.magicapp.features.cards.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Card(
    val id: String,
    val oracleId: String?,
    val name: String?,
    val typeLine: String?,
    val rarity: String?,
    val oracleText: String?,
    val loyalty: String?,
    val power: String?,
    val toughness: String?,
    val cmc: Double?,
    val manaCost: String?,
    val producedMana: List<String>?,
    val releasedAt: Date?,
    val imageSmall: String?,
    val imageCrop: String?,
    val set: String?,
    val setName: String?,
    val setType: String?,
    val collectorNumber: String?,
    val frontFace: Face?,
    val backFace: Face?,
    val legalities: Legalities?,
) : Parcelable {

    @Parcelize
    data class Face(
        val faceName: String?,
        val faceTypeLine: String?,
        val faceOracleText: String?,
        val facePower: String?,
        val faceToughness: String?,
        val faceLoyalty: String?,
        val faceArtist: String?,
        val faceImageSmall: String?,
        val faceImageNormal: String?,
        val faceCmc: Double?,
        val faceManaCost: String?,
        val faceProducedMana: List<String>?,
    ) : Parcelable

    @Parcelize
    data class Legalities(
        val standard: String?,
        val future: String?,
        val historic: String?,
        val timeless: String?,
        val gladiator: String?,
        val pioneer: String?,
        val explorer: String?,
        val modern: String?,
        val legacy: String?,
        val pauper: String?,
        val vintage: String?,
        val penny: String?,
        val commander: String?,
        val oathbreaker: String?,
        val standardBrawl: String?,
        val brawl: String?,
        val alchemy: String?,
        val pauperCommander: String?,
        val duel: String?,
        val oldschool: String?,
        val premodern: String?,
        val predh: String?,
    ) : Parcelable
}

@Parcelize
data class LegalityItem(
    val format: String,
    val legality: String
) : Parcelable