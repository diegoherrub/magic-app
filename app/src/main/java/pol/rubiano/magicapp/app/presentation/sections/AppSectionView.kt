package pol.rubiano.magicapp.app.presentation.sections

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import pol.rubiano.magicapp.databinding.SectionViewBinding

class AppSectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = SectionViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        orientation = HORIZONTAL
    }

    fun render(section: AppSectionUI) {
        binding.imageViewSectionIcon.setImageResource(section.getSectionImage())
        binding.textViewSectionTitle.text = section.getSectionTitle()
        binding.textViewSectionDescription.text = section.getSectionDescription()
    }
}