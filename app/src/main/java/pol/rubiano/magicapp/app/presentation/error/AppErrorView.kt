package pol.rubiano.magicapp.app.presentation.error

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.databinding.ErrorAppViewBinding

class AppErrorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : FrameLayout(context, attrs) {

    private val binding = ErrorAppViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        gone()
    }

    fun render(errorAppUI: ErrorAppUI) {
        binding.errorMessage.text = errorAppUI.getErrorMessage()
        visible()
    }
}
