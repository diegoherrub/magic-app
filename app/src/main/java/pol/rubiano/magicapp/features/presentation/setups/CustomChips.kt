package pol.rubiano.magicapp.features.presentation.setups

import android.annotation.SuppressLint
import androidx.core.view.iterator
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import pol.rubiano.magicapp.R

fun asignStyleChips(chipGroup: ChipGroup) {
    for (chip in chipGroup) {
        if (chip is Chip) {
            chip.setOnCheckedChangeListener { _, isChecked ->
                applyChipStyle(chip, isChecked)
            }
        }
    }
}

fun applyChipStyle(chip: Chip, isChecked: Boolean) {
    val context = chip.context
    if (isChecked) {
        chip.setChipBackgroundColorResource(R.color.md_theme_primary)
        chip.setTextColor(context.getColor(R.color.md_theme_onSecondary))
    } else {
        chip.setChipBackgroundColorResource(android.R.color.transparent)
        chip.setTextColor(context.getColor(R.color.md_theme_secondary))
    }
}