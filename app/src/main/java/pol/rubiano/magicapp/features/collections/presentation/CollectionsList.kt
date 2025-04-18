package pol.rubiano.magicapp.features.collections.presentation

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.CollectionsListBinding

class CollectionsList : Fragment() {

    private var _binding: CollectionsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()
    private val collectionsAdapter = CollectionsAdapter { collectionName ->
        val passCollectionClicked = CollectionsListDirections.actionCollectionsListToCollectionPanel(collectionName)
        findNavController().navigate(passCollectionClicked)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.collectionsList.layoutManager = LinearLayoutManager(requireContext())
        binding.collectionsList.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        binding.collectionsList.adapter = collectionsAdapter
        viewModel.loadCollections()
        setupObservers()
    }

    private fun setupObservers() {
        Log.d("@pol", "CollectionsList.setupObservers()")
        viewModel.userCollections.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.collectionsList.visibility = View.VISIBLE

                    Log.d(
                        "@pol",
                        "Entra en CollectionsList.setupObservers().Uisatate.Success()"
                    )
                    val collections = state.data
                    Log.d("@pol", "Collections: $collections")

                    Log.d(
                        "@pol",
                        "start -> CollectionsList.setupObservers().Uisatate.Success().submitList"
                    )
                    collectionsAdapter.submitList(collections)

                    Log.d(
                        "@pol",
                        "end -> CollectionsList.setupObservers().Uisatate.Success().submitList"
                    )
                }
                is UiState.Empty -> {
                    Log.d("@pol", "Start -> CollectionsList.setupObservers().Uisatate.Empty()")
                    //binding.collectionsList.visibility = View.GONE
                }

                is UiState.Error -> {
                    Log.d("@pol", "Entra en CollectionsList.setupObservers().Uisatate.Error()")
                    AppError.AppDataError
                }

                else -> {
                    Log.d(
                        "@pol",
                        "Start/End -> CollectionsList.setupObservers().Uisatate.Else()"
                    )
                }
            }
        }
        Log.d("@pol", "End -> CollectionsList.setupObservers")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
