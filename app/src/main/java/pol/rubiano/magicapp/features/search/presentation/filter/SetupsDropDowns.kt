package pol.rubiano.magicapp.features.search.presentation.filter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import pol.rubiano.magicapp.R

fun setupDropDown(view: View, viewComponent: View, chipGroupFilters: ChipGroup)  {
    val header = view.findViewById<TextView>(R.id.app_search_filter_card_name_filter)
    header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_down, 0)
    // header.descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
    header.setOnClickListener {
        if (viewComponent.isGone) {
            viewComponent.visibility = View.VISIBLE
            header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_up, 0)
//            imageToggle.setImageResource(R.drawable.arrow_drop_up)
        } else {
            viewComponent.visibility = View.GONE
            header.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_drop_down, 0)
//            imageToggle.setImageResource(R.drawable.arrow_drop_down)
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
