package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.NewCollectionFragmentBinding
import pol.rubiano.magicapp.features.collections.domain.Collection

class NewCollectionFragment : Fragment() {

    private var _binding: NewCollectionFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewCollectionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.currentCollection.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val actionWhenSaved = NewCollectionFragmentDirections.actionNewCollectionFragmentToCollectionsFragment()
                    findNavController().navigate(actionWhenSaved)
                }
                else -> {
                    Log.d("@pol", "fail -> NewCollectionFragment.setupObserver().no ha devuelto la colección actual al guardar")
                }
            }
        }
        binding.btnCreateNewCollection.setOnClickListener {
            viewModel.saveCollection(createCollection())
        }
    }

    private fun createCollection(): Collection{
        val name = binding.newCollectionName.text.toString().trim()
        if (name.isNotEmpty()) {
            return Collection(
                name = name,
                cards = emptyList()
            )
        }
        return Collection("New Collection", emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

