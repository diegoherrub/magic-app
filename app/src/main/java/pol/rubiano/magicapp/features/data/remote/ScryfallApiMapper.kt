package pol.rubiano.magicapp.features.data.remote

import pol.rubiano.magicapp.app.data.remote.toModel
import pol.rubiano.magicapp.features.domain.Scryfall

fun ScryfallApiModel.toModel(): Scryfall {
    return Scryfall(
        data = data?.map {it.toModel()},
        hasMore = hasMore,
        nextPage = nextPage,
        totalCards = totalCards
    )
}