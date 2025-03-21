package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.sections.AppSectionUI
import pol.rubiano.magicapp.databinding.MagicFragmentBinding

class MagicFragment : Fragment() {

    private var _binding: MagicFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MagicFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSections()
        bindSections()
    }

    private fun setupSections() {
        val sectionsMap = mapOf(
            binding.glossarySection to AppSectionUI(
                R.drawable.glossary,
                getString(R.string.glossary_title),
                getString(R.string.glossary_description),
                requireContext()
            ),
            binding.legalitiesSection to AppSectionUI(
                R.drawable.legalities,
                getString(R.string.legalities_title),
                getString(R.string.legalities_description),
                requireContext()
            ),
        )
        sectionsMap.forEach { (layout, section) ->
            layout.render(section)
        }
    }

    private fun bindSections() {
        binding.glossarySection.setOnClickListener {
            findNavController().navigate(MagicFragmentDirections.actionFromMagicToGlossary())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
