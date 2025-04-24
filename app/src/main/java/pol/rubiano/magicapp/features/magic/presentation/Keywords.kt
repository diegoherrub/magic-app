package pol.rubiano.magicapp.features.magic.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.xmlpull.v1.XmlPullParser
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.utils.mapManaSymbols
import pol.rubiano.magicapp.databinding.KeywordsListBinding
import pol.rubiano.magicapp.features.magic.domain.modules.Keyword
import pol.rubiano.magicapp.features.magic.presentation.adapters.KeywordsAdapter

class Keywords : Fragment() {

    private var _binding: KeywordsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: KeywordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = KeywordsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        adapter = KeywordsAdapter()

        binding.keywordList.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@Keywords.adapter
        }

        val keywords = loadKeywordsFromXml()
            .sortedBy { it.term.lowercase() }
        adapter.setKeywords(keywords)

        binding.keywordList.adapter = adapter

        binding.appKeywordsSearchBar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        binding.keywordList.adapter = adapter
    }

    private fun loadKeywordsFromXml(): List<Keyword> {
        val keywordsList = mutableListOf<Keyword>()
        val parser = requireContext().resources.getXml(R.xml.keywords)
        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG && parser.name == "entry") {
                val imageResId = parser.getAttributeResourceValue(null, "image", 0)
                val termResId = parser.getAttributeResourceValue(null, "term", 0)
                val informationResId = parser.getAttributeResourceValue(null, "information", 0)

                val term = requireContext().getString(termResId)
                val information =
                    mapManaSymbols(
                        requireContext(),
                        requireContext().getString(informationResId)
                    ).toString()

                keywordsList.add(
                    Keyword(
                        icon = imageResId,
                        term = term,
                        information = information
                    )
                )
            }
            eventType = parser.next()
        }
        return keywordsList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
