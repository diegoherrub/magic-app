package pol.rubiano.magicapp.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.ChipGroup
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.SearchFragmentBinding
import pol.rubiano.magicapp.features.domain.models.Filter
import pol.rubiano.magicapp.features.search.filter.FilterCardView
import pol.rubiano.magicapp.features.decks.DecksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.features.domain.models.Deck

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val args: SearchFragmentArgs by navArgs()

    private var deck: Deck? = null
    private var collectionName: String? = null
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
        deck = args.deck
        collectionName = args.collectionName

        editCardName = view.findViewById(R.id.edit_card_name)
        chipGroupFilters = view.findViewById(R.id.app_chip_group_filters)
        filtersContainer = view.findViewById(R.id.search_filter_cardviews_container)

        val raritiesOptions = resources.getStringArray(R.array.q_rarities_options).toList()
        val colorsOptions = resources.getStringArray(R.array.q_colors_options).toList()
        val typesOptions = resources.getStringArray(R.array.q_types_options).toList()

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
            getString(R.string.q_color_white) to "w",
            getString(R.string.q_color_blue) to "u",
            getString(R.string.q_color_black) to "b",
            getString(R.string.q_color_red) to "r",
            getString(R.string.q_color_green) to "g",
            getString(R.string.q_color_colorless) to "c"
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
        if (selectedRarities.isNotEmpty()) queryParts.add(selectedRarities)
        if (selectedColors.isNotEmpty()) queryParts.add(selectedColors)
        if (selectedTypes.isNotEmpty()) queryParts.add(selectedTypes)

        val query = queryParts.joinToString(" ")
//        val action = SearchFragmentDirections.actSearchToDeckDetails(deck!!)
//        findNavController().navigate(action)
        val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(
            query,
            deck
        )
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
