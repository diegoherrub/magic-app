package pol.rubiano.magicapp.features.presentation.ui.magic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pol.rubiano.magicapp.databinding.KeywordsFragmentBinding
import pol.rubiano.magicapp.features.data.local.loadKeywordsFromXml
import pol.rubiano.magicapp.features.presentation.adapters.KeywordsAdapter

class KeywordsFragment : Fragment() {
    private var _binding: KeywordsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: KeywordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = KeywordsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewKeyword.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewKeyword.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        val keywords = loadKeywordsFromXml(requireContext())
            .sortedBy { it.term.lowercase() }
        adapter = KeywordsAdapter()
        binding.recyclerViewKeyword.adapter = adapter
        adapter.setKeywords(keywords)
        binding.appKeywordsSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        binding.recyclerViewKeyword.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
