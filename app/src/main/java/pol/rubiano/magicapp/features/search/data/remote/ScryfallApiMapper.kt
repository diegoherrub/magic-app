package pol.rubiano.magicapp.features.search.data.remote

import pol.rubiano.magicapp.app.cards.data.remote.toModel
import pol.rubiano.magicapp.features.search.domain.models.Scryfall

fun ScryfallApiModel.toModel(): Scryfall {
    return Scryfall(
        data = data?.map {it.toModel()},
        hasMore = hasMore,
        nextPage = nextPage,
        totalCards = totalCards
    )
}