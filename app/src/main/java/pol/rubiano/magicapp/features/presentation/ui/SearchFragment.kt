package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.databinding.SearchFragmentBinding
import pol.rubiano.magicapp.features.presentation.viewmodels.SearchViewModel
import pol.rubiano.magicapp.features.domain.Filter
import pol.rubiano.magicapp.features.presentation.ui.search.FilterCardView

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()
    private lateinit var editCardName: EditText
    private lateinit var chipGroupFilters: ChipGroup
    private lateinit var filtersContainer: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editCardName = view.findViewById(R.id.edit_card_name)
        chipGroupFilters = view.findViewById(R.id.app_chip_group_filters)
        filtersContainer = view.findViewById(R.id.search_filter_cardviews_container)

        val raritiesOptions = resources.getStringArray(R.array.rarities_options).toList()
        val colorsOptions = resources.getStringArray(R.array.colors_options).toList()
        val typesOptions = resources.getStringArray(R.array.types_options).toList()

        val filters = listOf(
            Filter(getString(R.string.rarities), raritiesOptions),
            Filter(getString(R.string.colors), colorsOptions),
            Filter(getString(R.string.types), typesOptions)
        )

        filters.forEach { filter ->
            val filterCardView = FilterCardView(requireContext())
            filterCardView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            filterCardView.setup(filter.name, filter.options)
            filterCardView.configureDropDown(chipGroupFilters)
            filtersContainer.addView(filterCardView)
        }
    }

    private fun getSelectedRarities(): String {
        val selectedOptions = mutableListOf<String>()

        for (i in 0 until filtersContainer.childCount) {
            val view = filtersContainer.getChildAt(i)
            if (view is FilterCardView && view.tag == getString(R.string.rarities)) {
                selectedOptions.addAll(view.getSelectedOptions())
            }
        }

        return when {
            selectedOptions.isEmpty() -> ""
            selectedOptions.size == 1 -> "r:${selectedOptions.first()}"
            else -> selectedOptions.joinToString(
                separator = " or ",
                prefix = "(",
                postfix = ")"
            ) { "r:$it" }
        }
    }

    private fun getSelectedColors(): String {
        val selectedOptions = mutableListOf<String>()

        for (i in 0 until filtersContainer.childCount) {
            val view = filtersContainer.getChildAt(i)
            if (view is FilterCardView && view.tag == getString(R.string.colors)) {
                selectedOptions.addAll(view.getSelectedOptions())
            }
        }

        if (selectedOptions.isEmpty()) return ""

        val mapping = mapOf(
            getString(R.string.color_white) to "w",
            getString(R.string.color_blue) to "u",
            getString(R.string.color_black) to "b",
            getString(R.string.color_red) to "r",
            getString(R.string.color_green) to "g",
            getString(R.string.color_colorless) to "c"
        )

        val letters = selectedOptions.mapNotNull { mapping[it] }.joinToString(separator = "")

        return if (letters.isNotEmpty()) "c:$letters" else ""
    }

    private fun getSelectedTypes(): String {
        val selectedOptions = mutableListOf<String>()

        for (i in 0 until filtersContainer.childCount) {
            val view = filtersContainer.getChildAt(i)
            if (view is FilterCardView && view.tag == getString(R.string.types)) {
                selectedOptions.addAll(view.getSelectedOptions())
            }
        }

        return when {
            selectedOptions.isEmpty() -> ""
            selectedOptions.size == 1 -> "t:${selectedOptions.first()}"
            else -> selectedOptions.joinToString(
                separator = " or ",
                prefix = "(",
                postfix = ")"
            ) { "t:$it" }
        }
    }

    fun performSearch() {
        val queryParts = mutableListOf<String>()

        val nameInput = editCardName.text.toString().trim()
        val selectedRarities = getSelectedRarities()
        val selectedColors = getSelectedColors()
        val selectedTypes = getSelectedTypes()

        if (nameInput.isNotEmpty()) queryParts.add("name:\"$nameInput\"")
        if (view?.let { selectedRarities } != "") queryParts.add(selectedRarities)
        if (view?.let { selectedColors } != "") queryParts.add(selectedColors)
        if (view?.let { selectedTypes } != "") queryParts.add(selectedTypes)

        val query = queryParts.joinToString(" ")
        val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(query)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
