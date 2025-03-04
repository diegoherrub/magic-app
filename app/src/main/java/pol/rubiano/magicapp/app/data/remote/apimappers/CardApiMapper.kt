package pol.rubiano.magicapp.app.data.remote.apimappers

import pol.rubiano.magicapp.app.data.remote.apimodels.CardApiModel
import pol.rubiano.magicapp.app.domain.entities.Card
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun CardApiModel.toModel(): Card {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val parsedDate: Date? = releasedAt?.let {
        try {
            dateFormat.parse(it)
        } catch (e: Exception) {
            null
        }
    }

    return Card(
        id = id,
        oracleId = oracleId,
        name = name,
        typeLine = typeLine,
        rarity = rarity,
        oracleText = oracleText,
        loyalty = loyalty,
        power = power,
        toughness = toughness,
        cmc = cmc,
        manaCost = manaCost,
        producedMana = producedMana,
        releasedAt = parsedDate,
        artCrop = imageUris?.artCrop,
        borderCrop = imageUris?.borderCrop,
        set = set,
        setName = setName,
        setType = setType,
        collectorNumber = collectorNumber,
        frontFace = cardFaces?.getOrNull(0)?.let { face ->
            Card.FrontFace(
                frontFaceName = face.name,
                frontFaceTypeLine = face.typeLine,
                frontFaceOracleText = face.oracleText,
                frontFacePower = face.power,
                frontFaceToughness = face.toughness,
                frontFaceLoyalty = face.loyalty,
                frontFaceArtist = face.artist,
                frontArtCrop = face.imageUris?.artCrop,
                backBorderCrop = face.imageUris?.borderCrop,
                frontFaceCmc = face.cmc,
                frontFaceManaCost = face.manaCost,
                frontFaceProducedMana = face.producedMana
            )
        },
        backFace = cardFaces?.getOrNull(1)?.let { face ->
            Card.BackFace(
                backFaceName = face.name,
                backFaceTypeLine = face.typeLine,
                backFaceOracleText = face.oracleText,
                backFacePower = face.power,
                backFaceToughness = face.toughness,
                backFaceLoyalty = face.loyalty,
                backFaceArtist = face.artist,
                backArtCrop = face.imageUris?.artCrop,
                backBorderCrop = face.imageUris?.borderCrop,
                backFaceCmc = face.cmc,
                backFaceManaCost = face.manaCost,
                backFaceProducedMana = face.producedMana
            )
        },
        legalities = Card.Legalities(
            standard = legalities.standard,
            future = legalities.future,
            historic = legalities.historic,
            timeless = legalities.timeless,
            gladiator = legalities.gladiator,
            pioneer = legalities.pioneer,
            explorer = legalities.explorer,
            modern = legalities.modern,
            legacy = legalities.legacy,
            pauper = legalities.pauper,
            vintage = legalities.vintage,
            penny = legalities.penny,
            commander = legalities.commander,
            oathbreaker = legalities.oathbreaker,
            standardBrawl = legalities.standardBrawl,
            brawl = legalities.brawl,
            alchemy = legalities.alchemy,
            pauperCommander = legalities.pauperCommander,
            duel = legalities.duel,
            oldschool = legalities.oldschool,
            premodern = legalities.premodern,
            predh = legalities.predh
        )
    )
}