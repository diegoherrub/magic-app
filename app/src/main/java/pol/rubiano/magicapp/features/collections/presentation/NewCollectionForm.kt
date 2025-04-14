package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.NewCollectionFormBinding
import pol.rubiano.magicapp.features.collections.domain.Collection

class NewCollectionForm : Fragment() {

    private var _binding: NewCollectionFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewCollectionFormBinding.inflate(inflater, container, false)
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
                    val actionWhenSaved = NewCollectionFormDirections.actionNewCollectionFormToCollectionsList()
                    findNavController().navigate(actionWhenSaved)
                }
                else -> {
                    Log.d("@pol", "fail -> NewCollectionForm.setupObserver().no ha devuelto la colección actual al guardar")
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
                order = 1,
                cards = emptyList()
            )
        }
        val collectionName = getString(R.string.str_new_collection_title)
        return Collection(collectionName, 1,  emptyList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

