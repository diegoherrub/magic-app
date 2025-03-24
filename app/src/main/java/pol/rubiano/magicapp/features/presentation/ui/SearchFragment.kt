package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.SearchFragmentBinding
import pol.rubiano.magicapp.features.presentation.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    private lateinit var editCardName: EditText
    private lateinit var spinnerCardType: Spinner
    private lateinit var spinnerCardColor: Spinner
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
        spinnerCardColor = view.findViewById(R.id.spinner_card_color)
        btnCardSearch = view.findViewById(R.id.btn_card_search)
        setupSpinners()
        btnCardSearch.setOnClickListener {
            performSearch()
        }
    }


    private fun setupSpinners() {
        val types = listOf("", "artifact", "battle", "conspiracy", "creature", "dungeon", "enchantment", "instant", "kindred", "land", "phenomenon", "plane", "planeswalker", "scheme", "sorcery", "vanguard")
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, types)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCardType.adapter = typeAdapter

        // Opciones para el color de la carta
        // TODO - hacerlo mapa para poder poner los símbolos del maná entre corchetes y que vean las opciones con ese codigo
        val colors = listOf("", "white", "blue", "black", "red", "green", "colorless")
        val colorAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, colors)
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCardColor.adapter = colorAdapter
    }

    private fun performSearch() {
        val nameInput = editCardName.text.toString().trim()
        val selectedType = spinnerCardType.selectedItem.toString().trim()
        val selectedColor = spinnerCardColor.selectedItem.toString().trim()

        val queryParts = mutableListOf<String>()
        if (nameInput.isNotEmpty()) queryParts.add("name:\"$nameInput\"")
        if (selectedType.isNotEmpty()) queryParts.add("t:$selectedType")
        if (selectedColor.isNotEmpty()) queryParts.add("c:$selectedColor")
        val query = queryParts.joinToString(" ")

        val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(query)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
