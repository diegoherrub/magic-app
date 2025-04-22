package pol.rubiano.magicapp.features.cards.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.features.cards.data.local.CardLocalDataSource
import pol.rubiano.magicapp.features.cards.data.remote.CardRemoteDataSource
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.cards.domain.repositories.CardRepository
import pol.rubiano.magicapp.app.domain.AppError
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

@Single
class CardDataRepository(
    private val remote: CardRemoteDataSource,
    private val local: CardLocalDataSource,
    private val context: Context
) : CardRepository {

    override suspend fun saveCardToLocal(card: Card) {
        local.saveCardToLocal(card)
    }

    override suspend fun getLocalCardById(cardId: String): Card? {
        return local.getLocalCardById(cardId)
    }

    override suspend fun getRandomCard(): Result<Card> {
        val remoteCard = remote.getRandomCard()
        val card = remoteCard.getOrNull()
        if (card != null) {
            local.saveCardToLocal(card)

            card.imageCrop?.let { mainImageUrl ->
                val fileName = "${card.id}.png"
                downloadAndSaveImage(context, mainImageUrl, fileName)
            }
            card.imageSmall?.let { smallImageUrl ->
                val fileName = "${card.id}_small.png"
                downloadAndSaveImage(context, smallImageUrl, fileName)
            }

            card.frontFace?.faceImageNormal?.let { frontImageUrl ->
                val fileName = "${card.id}_front.png"
                downloadAndSaveImage(context, frontImageUrl, fileName)
            }
            card.frontFace?.faceImageSmall?.let { frontSmallImageUrl ->
                val fileName = "${card.id}_small_front.png"
                downloadAndSaveImage(context, frontSmallImageUrl, fileName)
            }

            card.backFace?.faceImageNormal?.let { backImageUrl ->
                val fileName = "${card.id}_back.png"
                downloadAndSaveImage(context, backImageUrl, fileName)
            }
            card.backFace?.faceImageSmall?.let { backSmallImageUrl ->
                val fileName = "${card.id}_small_back.png"
                downloadAndSaveImage(context, backSmallImageUrl, fileName)
            }

            return Result.success(remoteCard.getOrThrow())
        }
        return Result.failure(AppError.AppServerError)
    }

    private suspend fun downloadAndSaveImage(
        context: Context,
        imageUrl: String,
        fileName: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    return@withContext false
                }
                val inputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()

                val internalDir = context.filesDir
                val imageFile = File(internalDir, fileName)

                FileOutputStream(imageFile).use { fos: FileOutputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}

