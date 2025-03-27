package pol.rubiano.magicapp.features.presentation.ui.search

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import pol.rubiano.magicapp.R

class FilterCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val headerTextView: TextView
    private val switchContainer: LinearLayout

    init {
        LayoutInflater.from(context).inflate(R.layout.search_filters_cardview_layout, this, true)
        headerTextView = findViewById(R.id.app_search_filter_card_name_filter)
        switchContainer = findViewById(R.id.app_search_filter_card_switch_container)
    }

    fun setup(filterName: String, options: List<String>) {
        headerTextView.text = filterName
        tag = filterName
        switchContainer.removeAllViews()

        options.forEach { option ->
            val materialSwitch = MaterialSwitch(context)
            materialSwitch.text = option
            switchContainer.addView(materialSwitch)
        }
    }

    fun getSelectedOptions(): List<String> {
        val selected = mutableListOf<String>()
        for (i in 0 until switchContainer.childCount) {
            val child = switchContainer.getChildAt(i)
            if (child is MaterialSwitch && child.isChecked) {
                selected.add(child.text.toString())
            }
        }
        return selected
    }

    fun unselectOption(option: String) {
        for (i in 0 until switchContainer.childCount) {
            val child = switchContainer.getChildAt(i)
            if (child is MaterialSwitch && child.text == option) {
                child.isChecked = false
            }
        }
    }

    fun configureDropDown(chipGroupFilters: ChipGroup) {
        setupDropDown(this, switchContainer, chipGroupFilters)
    }
}
