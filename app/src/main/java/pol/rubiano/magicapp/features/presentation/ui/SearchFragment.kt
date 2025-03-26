package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.ChipGroup
import com.google.android.material.materialswitch.MaterialSwitch
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.SearchFragmentBinding
import pol.rubiano.magicapp.features.presentation.viewmodels.SearchViewModel
import pol.rubiano.magicapp.features.domain.Filter
import pol.rubiano.magicapp.features.presentation.ui.search.FilterCardView
import pol.rubiano.magicapp.features.presentation.ui.search.setupDropDown

class SearchFragment : Fragment() {

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModel()

    private lateinit var chipGroupFilters: ChipGroup
    private lateinit var filtersContainer: LinearLayout
//    private lateinit var editCardName: EditText
    private lateinit var selectedRarities: MaterialSwitch
//    private lateinit var selectedColors: ChipGroup
//    private lateinit var selectedTypes: ChipGroup
    private lateinit var btnCardSearch: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            // Instanciamos nuestro FilterCardView
            val filterCardView = FilterCardView(requireContext())
            filterCardView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            filterCardView.setup(filter.name, filter.options)
            filterCardView.configureDropDown(chipGroupFilters)
            filtersContainer.addView(filterCardView)
        }

//        editCardName = view.findViewById(R.id.edit_card_name)
//        selectedRarities = view.findViewById(R.id.search_rarities_switch_container)
//        selectedColors = view.findViewById(R.id.search_colors_container)
//        selectedTypes = view.findViewById(R.id.search_types_container)
        btnCardSearch = view.findViewById(R.id.btn_card_search)

        btnCardSearch.setOnClickListener { performSearch() }

//        // Ejemplo: Configuración de la acción del botón (si lo requieres)
//        val buttonSend: Button = view.findViewById(R.id.buttonSend)
//        buttonSend.setOnClickListener {
//            // Aquí podrías procesar la consulta, por ejemplo, obteniendo el estado de los switches
//            val commonChecked = view.findViewById<com.google.android.material.materialswitch.MaterialSwitch>(R.id.switchCommon).isChecked
//            val uncommonChecked = view.findViewById<com.google.android.material.materialswitch.MaterialSwitch>(R.id.switchUncommon).isChecked
//            val rareChecked = view.findViewById<com.google.android.material.materialswitch.MaterialSwitch>(R.id.switchRare).isChecked
//            val mythicChecked = view.findViewById<com.google.android.material.materialswitch.MaterialSwitch>(R.id.switchMythic).isChecked
//
//            Log.d("SearchFragment", "Común: $commonChecked, Infrecuente: $uncommonChecked, Rara: $rareChecked, Mítica: $mythicChecked")
//            // Aquí construyes la consulta y navegas a la siguiente pantalla
//            // val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(query)
//            // findNavController().navigate(action)
//        }
    }

    private fun getSelectedRarities(): String {
        val selectedOptions = mutableListOf<String>()

        for (i in 0 until filtersContainer.childCount) {
            val view = filtersContainer.getChildAt(i)
            // Verifica que la vista sea FilterCardView y que su tag coincida con "rarities"
            if (view is FilterCardView && view.tag == getString(R.string.rarities)) {
                selectedOptions.addAll(view.getSelectedOptions())
            }
        }

        return when {
            selectedOptions.isEmpty() -> ""
            selectedOptions.size == 1 -> "r:${selectedOptions.first()}"
            else -> selectedOptions.joinToString(separator = " or ", prefix = "(", postfix = ")") { "r:$it" }
        }
    }

//    private fun getSelectedRarities(): String {
//        val selectedOptions = mutableListOf<String>()
//
//        // Recorremos cada tarjeta de filtro generada
//        for (i in 0 until filtersContainer.childCount) {
//            val filterCard = filtersContainer.getChildAt(i)
//            // Verificamos si esta tarjeta corresponde a las rarities
//            if (filterCard.tag == getString(R.string.rarities)) {
//                val switchContainer = filterCard.findViewById<LinearLayout>(R.id.app_search_filter_card_switch_container)
//                for (j in 0 until switchContainer.childCount) {
//                    val child = switchContainer.getChildAt(j)
//                    if (child is MaterialSwitch && child.isChecked) {
//                        // Aquí child.text contiene el valor mostrado (por ejemplo, "Common", "Uncommon", etc.)
//                        selectedOptions.add(child.text.toString())
//                    }
//                }
//            }
//        }
//
//        // Construimos la cadena de consulta, por ejemplo:
//        return when {
//            selectedOptions.isEmpty() -> ""
//            selectedOptions.size == 1 -> "r:${selectedOptions.first()}"
//            else -> selectedOptions.joinToString(separator = " or ", prefix = "(", postfix = ")") { "r:$it" }
//        }
//    }

//    private fun getSelectedRarities(view: View): String {
//        val selectedRarities = mutableListOf<String>()
//        if (view.findViewById<Chip>(R.id.chip_common).isChecked) selectedRarities.add("common")
//        if (view.findViewById<Chip>(R.id.chip_uncommon).isChecked) selectedRarities.add("uncommon")
//        if (view.findViewById<Chip>(R.id.chip_rare).isChecked) selectedRarities.add("rare")
//        if (view.findViewById<Chip>(R.id.chip_mythic).isChecked) selectedRarities.add("mythic")
//
//        return when {
//            selectedRarities.isEmpty() -> ""
//            selectedRarities.size == 1 -> "r:${selectedRarities.first()}"
//            else -> selectedRarities.joinToString(
//                prefix = "(",
//                postfix = ")",
//                separator = " or "
//            ) { "r:$it" }
//        }
//    }

//    private fun getSelectedRarities(view: View): String {
//        val selectedRarities = mutableListOf<String>()
//        if (view.findViewById<MaterialSwitch>(R.id.switch_common).isChecked) selectedRarities.add("common")
//        if (view.findViewById<MaterialSwitch>(R.id.switch_uncommon).isChecked) selectedRarities.add("uncommon")
//        if (view.findViewById<MaterialSwitch>(R.id.switch_rare).isChecked) selectedRarities.add("rare")
//        if (view.findViewById<MaterialSwitch>(R.id.switch_mythic).isChecked) selectedRarities.add("mythic")
//
//        return when {
//            selectedRarities.isEmpty() -> ""
//            selectedRarities.size == 1 -> "r:${selectedRarities.first()}"
//            else -> selectedRarities.joinToString(
//                prefix = "(",
//                postfix = ")",
//                separator = " or "
//            ) { "r:$it" }
//        }
//    }

//    private fun getSelectedColors(view: View): String {
//        val selectedColors = StringBuilder("c:")
//        if (view.findViewById<Chip>(R.id.chip_white).isChecked) selectedColors.append("w")
//        if (view.findViewById<Chip>(R.id.chip_blue).isChecked) selectedColors.append("u")
//        if (view.findViewById<Chip>(R.id.chip_black).isChecked) selectedColors.append("b")
//        if (view.findViewById<Chip>(R.id.chip_red).isChecked) selectedColors.append("r")
//        if (view.findViewById<Chip>(R.id.chip_green).isChecked) selectedColors.append("g")
//        return if (selectedColors.length > 2) selectedColors.toString() else ""
//    }

//    private fun getSelectedTypes(view: View): String {
//        val selectedTypes = mutableListOf<String>()
//        if (view.findViewById<Chip>(R.id.chip_artifact).isChecked) selectedTypes.add("artifact")
//        if (view.findViewById<Chip>(R.id.chip_battle).isChecked) selectedTypes.add("battle")
//        if (view.findViewById<Chip>(R.id.chip_conspiracy).isChecked) selectedTypes.add("conspiracy")
//        if (view.findViewById<Chip>(R.id.chip_creature).isChecked) selectedTypes.add("creature")
//        if (view.findViewById<Chip>(R.id.chip_dungeon).isChecked) selectedTypes.add("dungeon")
//        if (view.findViewById<Chip>(R.id.chip_enchantment).isChecked) selectedTypes.add("enchantment")
//        if (view.findViewById<Chip>(R.id.chip_instant).isChecked) selectedTypes.add("instant")
//        if (view.findViewById<Chip>(R.id.chip_kindred).isChecked) selectedTypes.add("kindred")
//        if (view.findViewById<Chip>(R.id.chip_land).isChecked) selectedTypes.add("land")
//        if (view.findViewById<Chip>(R.id.chip_phenomenon).isChecked) selectedTypes.add("phenomenon")
//        if (view.findViewById<Chip>(R.id.chip_plane).isChecked) selectedTypes.add("plane")
//        if (view.findViewById<Chip>(R.id.chip_planeswalker).isChecked) selectedTypes.add("planeswalker")
//        if (view.findViewById<Chip>(R.id.chip_scheme).isChecked) selectedTypes.add("scheme")
//        if (view.findViewById<Chip>(R.id.chip_sorcery).isChecked) selectedTypes.add("sorcery")
//        if (view.findViewById<Chip>(R.id.chip_vanguard).isChecked) selectedTypes.add("vanguard")
//
//        return when {
//            selectedTypes.isEmpty() -> ""
//            selectedTypes.size == 1 -> "t:${selectedTypes.first()}"
//            else -> selectedTypes.joinToString(
//                prefix = "(",
//                postfix = ")",
//                separator = " or "
//            ) { "t:$it" }
//        }
//    }

    private fun performSearch() {
        val queryParts = mutableListOf<String>()

//        val nameInput = editCardName.text.toString().trim()
        val selectedRarities = getSelectedRarities()
//        val selectedColors = getSelectedColors(requireView())
//        val selectedTypes = getSelectedTypes(requireView())

//        if (nameInput.isNotEmpty()) queryParts.add("name:\"$nameInput\"")
//        if (view?.let { selectedRarities } != "") queryParts.add(selectedRarities)
//        if (view?.let { selectedColors } != "") queryParts.add(selectedColors)
//        if (view?.let { selectedColors } != "") queryParts.add(selectedColors)
//        if (view?.let { selectedTypes } != "") queryParts.add(selectedTypes)

        val query = queryParts.joinToString(" ")
        val action = SearchFragmentDirections.actionSearchFragmentToResultsFragment(query)
        findNavController().navigate(action)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
