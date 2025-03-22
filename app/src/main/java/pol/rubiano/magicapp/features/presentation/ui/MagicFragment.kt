package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.MagicFragmentBinding
import pol.rubiano.magicapp.databinding.ViewCommonSectionListTypeItemBinding

class MagicFragment : Fragment() {

    private var _binding: MagicFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var glossary: ViewCommonSectionListTypeItemBinding
    private lateinit var legalities: ViewCommonSectionListTypeItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MagicFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        glossary = ViewCommonSectionListTypeItemBinding.bind(binding.root.findViewById(R.id.glossary))
        legalities = ViewCommonSectionListTypeItemBinding.bind(binding.root.findViewById(R.id.legalities))

        setupViews()
    }

    private fun setupViews() {
        setupSection(glossary, R.drawable.glossary, R.string.glossary_title, R.string.glossary_content) {
            findNavController().navigate(MagicFragmentDirections.actionFromMagicToGlossary())
        }

        setupSection(legalities, R.drawable.legalities, R.string.legality_title, R.string.legality_content) {
            findNavController().navigate(MagicFragmentDirections.actionFromMagicToLegality())
        }
    }

    private fun setupSection(
        section: ViewCommonSectionListTypeItemBinding,
        iconRes: Int,
        titleRes: Int,
        contentRes: Int,
        onClick: () -> Unit
    ) {
        section.viewCommonSectionListTypeIcon.setImageResource(iconRes)
        section.viewCommonSectionListTypeTitle.text = getString(titleRes)
        section.viewCommonSectionListTypeContent.text = getString(contentRes)
        section.root.setOnClickListener { onClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}