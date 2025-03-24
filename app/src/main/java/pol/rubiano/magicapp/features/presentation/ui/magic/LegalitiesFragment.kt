package pol.rubiano.magicapp.features.presentation.ui.magic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pol.rubiano.magicapp.databinding.LegalitiesFragmentBinding
import pol.rubiano.magicapp.features.data.local.loadLegalitiesFromXml
import pol.rubiano.magicapp.features.presentation.adapters.LegalitiesAdapter

class LegalitiesFragment : Fragment() {
    private var _binding: LegalitiesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LegalitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LegalitiesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewLegalities.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewLegalities.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )

        val legalities = loadLegalitiesFromXml(requireContext())
        adapter = LegalitiesAdapter()
        binding.recyclerViewLegalities.adapter = adapter

        adapter.submitList(legalities)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
