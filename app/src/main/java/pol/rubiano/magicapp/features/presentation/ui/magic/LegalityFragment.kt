package pol.rubiano.magicapp.features.presentation.ui.magic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import pol.rubiano.magicapp.databinding.LegalityFragmentBinding
import pol.rubiano.magicapp.features.data.local.loadLegalityFromXml
import pol.rubiano.magicapp.features.presentation.adapters.LegalityAdapter

class LegalityFragment : Fragment() {
    private var _binding: LegalityFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LegalityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LegalityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appRecyclerViewLegalities.layoutManager = LinearLayoutManager(context)

        // Cargamos y ordenamos los términos
        val legalityTerms = loadLegalityFromXml(requireContext())
        val sortedTerms = legalityTerms.sortedBy { it.term }

        adapter = LegalityAdapter(sortedTerms)
        binding.appRecyclerViewLegalities.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
