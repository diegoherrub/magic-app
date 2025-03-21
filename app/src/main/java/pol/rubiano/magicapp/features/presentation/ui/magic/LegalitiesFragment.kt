package pol.rubiano.magicapp.features.presentation.ui.magic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pol.rubiano.magicapp.app.presentation.styckyheader.StickyHeaderDecoration
import pol.rubiano.magicapp.databinding.GlossaryFragmentBinding
import pol.rubiano.magicapp.features.data.local.groupGlossaryTerms
import pol.rubiano.magicapp.features.data.local.loadGlossaryFromXml
import pol.rubiano.magicapp.features.presentation.adapters.GlossaryAdapter

class LegalitiesFragment : Fragment() {
    private var _binding: GlossaryFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GlossaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GlossaryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding.recyclerViewGlossary.layoutManager = LinearLayoutManager(context)
//
//        // Cargamos y agrupamos los términos
//        val glossaryTerms = loadGlossaryFromXml(requireContext())
//        val groupedItems = groupGlossaryTerms(glossaryTerms.sortedBy { it.term })
//        adapter = GlossaryAdapter(groupedItems)
//        binding.recyclerViewGlossary.adapter = adapter
//
//        // Configuramos el SearchView para filtrar
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                adapter.filter.filter(query)
//                return false
//            }
//            override fun onQueryTextChange(newText: String?): Boolean {
//                adapter.filter.filter(newText)
//                return false
//            }
//        })
//
//        binding.recyclerViewGlossary.addItemDecoration(StickyHeaderDecoration(adapter))
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
