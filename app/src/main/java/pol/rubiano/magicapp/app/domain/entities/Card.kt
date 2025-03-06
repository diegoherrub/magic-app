package pol.rubiano.magicapp.app.domain.entities

import java.util.Date

data class Card(
    val id: String,                                 // Unique identifier of the card in Scryfall
    val oracleId: String?,                          // Identifier shared across all printings of this card
    val name: String?,                              // Name of the card
    val typeLine: String?,                          // Card type line (e.g., "Creature — Angel")
    val rarity: String?,                            // Card rarity (e.g., "common", "rare", "mythic")
    val oracleText: String?,                        // Oracle text describing the card's effect
    val loyalty: String?,                           // Loyalty points for Planeswalkers, null otherwise
    val power: String?,                             // Power of the creature (e.g., "4"), null if not a creature
    val toughness: String?,                         // Toughness of the creature (e.g., "4"), null if not a creature
    val cmc: Double?,                               // Converted mana cost (numeric value of mana cost)
    val manaCost: String?,                          // Mana cost needed to cast the card (e.g., "{3}{W}{W}")
    val producedMana: List<String>?,                // Types of mana the card can produce
    val releasedAt: Date?,                          // Date the card was released
    val borderCrop: String?,                        // Principal image of the card
    val set: String?,                               // Set code of the card
    val setName: String?,                           // Name of the set
    val setType: String?,                           // Type of the set
    val collectorNumber: String?,                   // Collector number of the card in the set
    val frontFace: Face?,                           // Information about the card's front face when it has multiple faces
    val backFace: Face?,                            // Information about the card's back face when it has multiple faces
    val legalities: Legalities?,                    // Legalities of the card in different formats
) {

    data class Face(
        val faceName: String?,
        val faceTypeLine: String?,
        val faceOracleText: String?,
        val facePower: String?,
        val faceToughness: String?,
        val faceLoyalty: String?,
        val faceArtist: String?,
        val faceBorderCrop: String?,
        val faceCmc: Double?,
        val faceManaCost: String?,
        val faceProducedMana: List<String>?,
    )

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
    )
}

data class LegalityItem(
    val format: String,
    val legality: String
)