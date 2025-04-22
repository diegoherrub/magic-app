package pol.rubiano.magicapp.features.search.presentation.filter

import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

fun addChip(chipText: String, chipGroupFilters: ChipGroup, view: View) {
    for (i in 0 until chipGroupFilters.childCount) {
        val chip = chipGroupFilters.getChildAt(i) as? Chip
        if (chip?.text == chipText) {
            return
        }
    }

    val chip = Chip(view.context).apply {
        text = chipText
        isCloseIconVisible = true
        setOnCloseIconClickListener {
            chipGroupFilters.removeView(this)
            (view as? FilterCardView)?.unselectOption(chipText)
        }
    }
    chipGroupFilters.addView(chip)
}


fun removeChip(chipText: String, chipGroupFilters: ChipGroup) {
    for (i in 0 until chipGroupFilters.childCount) {
        val chip = chipGroupFilters.getChildAt(i) as? Chip
        if (chip?.text == chipText) {
            chipGroupFilters.removeView(chip)
            break
        }
    }
}