package pol.rubiano.magicapp.features.magic.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.xmlpull.v1.XmlPullParser
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.utils.mapManaSymbols
import pol.rubiano.magicapp.databinding.LegalFormatsListBinding
import pol.rubiano.magicapp.features.magic.domain.modules.LegalFormat
import pol.rubiano.magicapp.features.magic.presentation.adapters.LegalFormatsAdapter

class LegalFormats : Fragment() {

    private var _binding: LegalFormatsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: LegalFormatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LegalFormatsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        adapter = LegalFormatsAdapter()
        binding.legalFormatsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@LegalFormats.adapter
        }
        val legalFormats = loadLegalFormatsFromXml()
        adapter.submitList(legalFormats)
    }

    private fun loadLegalFormatsFromXml(): List<LegalFormat> {
        val legalFormats = mutableListOf<LegalFormat>()
        val parser = requireContext().resources.getXml(R.xml.legal_formats)
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

                legalFormats.add(
                    LegalFormat(
                        icon = imageResId,
                        term = term,
                        information = information
                    )
                )
            }
            eventType = parser.next()
        }
        return legalFormats
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
