package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.SearchFragmentBinding
import pol.rubiano.magicapp.features.presentation.setups.asignStyleChips
import pol.rubiano.magicapp.features.presentation.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    private lateinit var editCardName: EditText

    private lateinit var selectedRarities: ChipGroup
    private lateinit var selectedColors: ChipGroup
    private lateinit var spinnerCardType: Spinner
    private lateinit var btnCardSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editCardName = view.findViewById(R.id.edit_card_name)
        spinnerCardType = view.findViewById(R.id.spinner_card_type)
        btnCardSearch = view.findViewById(R.id.btn_card_search)
        setupSpinners()

        selectedRarities = view.findViewById(R.id.search_rarities_container)
        asignStyleChips(selectedRarities)
        selectedColors = view.findViewById(R.id.search_colors_container)
        asignStyleChips(selectedColors)

        btnCardSearch.setOnClickListener {
            performSearch()
        }
    }

//    private fun getSelectedRarities(view: View): String {
//        val selectedRarities = StringBuilder("r:")
//        if (view.findViewById<Chip>(R.id.chip_common).isChecked) selectedRarities.append("common")
//        if (view.findViewById<Chip>(R.id.chip_uncommon).isChecked) selectedRarities.append("uncommon")
//        if (view.findViewById<Chip>(R.id.chip_rare).isChecked) selectedRarities.append("rare")
//        if (view.findViewById<Chip>(R.id.chip_mythic).isChecked) selectedRarities.append("mythic")
//        return if (selectedRarities.length > 2) selectedRarities.toString() else ""
//    }

    private fun getSelectedRarities(view: View): String {
        val selectedRarities = mutableListOf<String>()
        if (view.findViewById<Chip>(R.id.chip_common).isChecked) selectedRarities.add("common")
        if (view.findViewById<Chip>(R.id.chip_uncommon).isChecked) selectedRarities.add("uncommon")
        if (view.findViewById<Chip>(R.id.chip_rare).isChecked) selectedRarities.add("rare")
        if (view.findViewById<Chip>(R.id.chip_mythic).isChecked) selectedRarities.add("mythic")

        return when {
            selectedRarities.isEmpty() -> ""
            selectedRarities.size == 1 -> "r:${selectedRarities.first()}"
            else -> selectedRarities.joinToString(prefix = "(", postfix = ")", separator = " or ") { "r:$it" }
        }
    }

//


    private fun getSelectedColors(view: View): String {
        val selectedColors = StringBuilder("c:")
        if (view.findViewById<Chip>(R.id.chip_white).isChecked) selectedColors.append("w")
        if (view.findViewById<Chip>(R.id.chip_blue).isChecked) selectedColors.append("u")
        if (view.findViewById<Chip>(R.id.chip_black).isChecked) selectedColors.append("b")
        if (view.findViewById<Chip>(R.id.chip_red).isChecked) selectedColors.append("r")
        if (view.findViewById<Chip>(R.id.chip_green).isChecked) selectedColors.append("g")
        return if (selectedColors.length > 2) selectedColors.toString() else ""
    }



    private fun setupSpinners() {
        val types = listOf("", "artifact", "battle", "conspiracy", "creature", "dungeon", "enchantment", "instant", "kindred", "land", "phenomenon", "plane", "planeswalker", "scheme", "sorcery", "vanguard")
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCardType.adapter = typeAdapter
    }

    private fun performSearch() {
        val nameInput = editCardName.text.toString().trim()
        val selectedRarities = getSelectedRarities(requireView())
        val selectedColor = getSelectedColors(requireView())
        val selectedType = spinnerCardType.selectedItem.toString().trim()


        val queryParts = mutableListOf<String>()
        if (nameInput.isNotEmpty()) queryParts.add("name:\"$nameInput\"")
        if (selectedType.isNotEmpty()) queryParts.add("t:$selectedType")

        if (view?.let { selectedRarities } != "") queryParts.add(selectedRarities)
        if (view?.let { getSelectedColors(it).isNotEmpty() } == true) queryParts.add(selectedColor)

        val query = queryParts.joinToString(" ")
        val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(query)
        findNavController().navigate(action)
    }

//    override fun onResume() {
//        super.onResume()
//        asignStyleChips(selectedRarities)
//        asignStyleChips(selectedColors)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
