package pol.rubiano.magicapp.app.common.extensions

import android.content.Context
import android.widget.ImageView
import coil.load
import pol.rubiano.magicapp.R
import java.io.File

fun ImageView.loadUrl(url: String) {
    this.load(url) {
        placeholder(R.drawable.card_back)
    }
}

fun ImageView.loadImage(context: Context, cardId: String) {
    val imageFile = File(context.filesDir, "${cardId}_small.png")
    this.load(imageFile) {
        placeholder(R.drawable.card_back)
        error(R.drawable.card_back)
        // Opcional: desactiva caché para imágenes locales que pueden cambiar
        memoryCachePolicy(coil.request.CachePolicy.DISABLED)
        diskCachePolicy(coil.request.CachePolicy.DISABLED)
    }
}