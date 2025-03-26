package pol.rubiano.magicapp.features.presentation.ui.search

import android.view.View
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import pol.rubiano.magicapp.R

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
            when (chipText) {
                "Común" -> view.findViewById<MaterialSwitch>(R.id.switch_common)?.isChecked = false
                "Infrecuente" -> view.findViewById<MaterialSwitch>(R.id.switch_uncommon)?.isChecked = false
                "Rara" -> view.findViewById<MaterialSwitch>(R.id.switch_rare)?.isChecked = false
                "Mítica" -> view.findViewById<MaterialSwitch>(R.id.switch_mythic)?.isChecked = false
            }
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