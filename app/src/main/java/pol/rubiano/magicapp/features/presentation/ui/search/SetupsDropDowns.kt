package pol.rubiano.magicapp.features.presentation.ui.search

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isGone
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import pol.rubiano.magicapp.R

fun setupDropDown(view: View, viewComponent: View, chipGroupFilters: ChipGroup)  {
    val header = view.findViewById<LinearLayout>(R.id.headerRarities)
    val imageToggle = view.findViewById<ImageView>(R.id.imageToggleRarities)
    header.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
    header.setOnClickListener {
        if (viewComponent.isGone) {
            viewComponent.visibility = View.VISIBLE
            imageToggle.setImageResource(R.drawable.arrow_drop_up)
        } else {
            viewComponent.visibility = View.GONE
            imageToggle.setImageResource(R.drawable.arrow_drop_down)
        }
    }

    val switches = mutableListOf<Pair<MaterialSwitch, String>>()
    for (i in 0 until (viewComponent as ViewGroup).childCount) {
        val child = viewComponent.getChildAt(i)
        if (child is MaterialSwitch) {
            switches.add(child to child.text.toString())
        }
    }

    for ((switch, chipText) in switches) {
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addChip(chipText, chipGroupFilters, view)
            } else {
                removeChip(chipText, chipGroupFilters)
            }
        }
    }
}
