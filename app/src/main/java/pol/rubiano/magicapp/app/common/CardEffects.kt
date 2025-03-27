package pol.rubiano.magicapp.app.common

import android.widget.ImageView

object CardEffects {

    fun configureCardImageView(imageView: ImageView, density: Float) {
        imageView.post {
            imageView.pivotX = imageView.width / 2f
            imageView.pivotY = imageView.height / 2f
            imageView.cameraDistance = 8000 * density
        }
    }

    inline fun flip(
        imageView: ImageView,
        duration: Long = 300,
        crossinline flipAction: () -> Unit = { }
    ) {
        imageView.animate().rotationY(90f).setDuration(duration)
            .withEndAction {
                flipAction()
                imageView.rotationY = -90f
                imageView.animate().rotationY(0f).setDuration(duration).start()
            }.start()
    }
}
