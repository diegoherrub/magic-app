package pol.rubiano.magicapp.features.cards.data.remote

import pol.rubiano.magicapp.features.cards.domain.models.Card
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
        imageSmall = imageUris?.imageSmall,
        imageCrop = imageUris?.imageCrop,
        set = set,
        setName = setName,
        setType = setType,
        collectorNumber = collectorNumber,
        frontFace = cardFaces?.getOrNull(0)?.let { face ->
            Card.Face(
                faceName = face.name,
                faceTypeLine = face.typeLine,
                faceOracleText = face.oracleText,
                facePower = face.power,
                faceToughness = face.toughness,
                faceLoyalty = face.loyalty,
                faceArtist = face.artist,
                faceImageSmall = face.imageUris?.imageSmall,
                faceImageNormal = face.imageUris?.imageCrop,
                faceCmc = face.cmc,
                faceManaCost = face.manaCost,
                faceProducedMana = face.producedMana
            )
        },
        backFace = cardFaces?.getOrNull(1)?.let { face ->
            Card.Face(
                faceName = face.name,
                faceTypeLine = face.typeLine,
                faceOracleText = face.oracleText,
                facePower = face.power,
                faceToughness = face.toughness,
                faceLoyalty = face.loyalty,
                faceArtist = face.artist,
                faceImageSmall = face.imageUris?.imageSmall,
                faceImageNormal = face.imageUris?.imageCrop,
                faceCmc = face.cmc,
                faceManaCost = face.manaCost,
                faceProducedMana = face.producedMana
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