package pol.rubiano.magicapp.features.magic.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.databinding.MagicBinding
import pol.rubiano.magicapp.databinding.ViewCommonSectionListTypeItemBinding

class Magic : Fragment() {

    private var _binding: MagicBinding? = null
    private val binding get() = _binding!!

    private lateinit var keywords: ViewCommonSectionListTypeItemBinding
    private lateinit var legalFormats: ViewCommonSectionListTypeItemBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MagicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keywords =
            ViewCommonSectionListTypeItemBinding.bind(binding.root.findViewById(R.id.glossary))
        legalFormats =
            ViewCommonSectionListTypeItemBinding.bind(binding.root.findViewById(R.id.legalFormats))

        setupViews()
    }

    private fun setupViews() {
        setupSection(
            keywords,
            R.drawable.glossary,
            R.string.keyword_title,
            R.string.keyword_content
        ) {
            findNavController().navigate(MagicDirections.actFromMagicToKeywordsFragment())
        }

        setupSection(
            legalFormats,
            R.drawable.legalities,
            R.string.str_legalFormats,
            R.string.legality_content
        ) {
            findNavController().navigate(MagicDirections.actFromMagicToLegalFormats())
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