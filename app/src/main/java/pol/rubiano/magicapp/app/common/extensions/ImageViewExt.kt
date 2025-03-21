package pol.rubiano.magicapp.app.common.extensions

import android.widget.ImageView
import coil.load
import pol.rubiano.magicapp.R

fun ImageView.loadUrl(url: String) {
    this.load(url) {
        placeholder(R.drawable.card_back)
    }
}