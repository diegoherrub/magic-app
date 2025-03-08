package pol.rubiano.magicapp.app.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import pol.rubiano.magicapp.app.data.local.datasources.CardLocalDataSource
import pol.rubiano.magicapp.app.data.remote.datasources.CardRemoteDataSource
import pol.rubiano.magicapp.app.domain.ErrorApp
import pol.rubiano.magicapp.app.domain.entities.Card
import pol.rubiano.magicapp.app.domain.repositories.CardRepository
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

    override suspend fun getRandomCard(): Result<Card> {

        val remoteCard = remote.getRandomCard()
        val card = remoteCard.getOrNull()
        if (card != null) {
            // TODO - comprobar si está en local
            local.saveCardToLocal(card)

            card.borderCrop?.let { mainImageUrl ->
                val fileName = "${card.id}.png"
                downloadAndSaveImage(context, mainImageUrl, fileName)
            }

            card.frontFace?.faceBorderCrop?.let { frontImageUrl ->
                val fileName = "${card.id}_front.png"
                downloadAndSaveImage(context, frontImageUrl, fileName)
            }

            card.backFace?.faceBorderCrop?.let { backImageUrl ->
                val fileName = "${card.id}_back.png"
                downloadAndSaveImage(context, backImageUrl, fileName)
            }

            return Result.success(remoteCard.getOrThrow())
        }
        return Result.failure(ErrorApp.ServerErrorApp)
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

