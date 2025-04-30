package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.databinding.CollectionsListBinding
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsAdapter
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsViewModel

class CollectionsList : Fragment() {

    private var _binding: CollectionsListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()
    private var adapter = CollectionsAdapter { collectionName ->
        val passCollectionClicked =
            CollectionsListDirections.actFromCollectionsListToCollectionPanel(collectionName)
        findNavController().navigate(passCollectionClicked)
    }
    private val errorFactory: AppErrorUIFactory by inject()

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
        setupToolbar()
        setupView()
        setupObservers()
    }

    private fun setupToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.item_menu_add_new_collection -> {
                    findNavController().navigate(
                        CollectionsListDirections.actFromCollectionsListToNewCollectionForm()
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun setupView() {
        binding.collectionsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@CollectionsList.adapter
        }
        viewModel.loadCollections()
    }

    private fun setupObservers() {
        viewModel.fetchedCollections.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.collectionsList.visibility = View.VISIBLE
                    val collections = state.data
                    adapter.submitList(collections)
                }

                is UiState.Empty -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.str_noCollectionsYet,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is UiState.Error -> {
                    bindError(state.error)
                }

                else -> {}
            }
        }
    }

    private fun bindError(appError: AppError?) {
        if (appError != null) {
            binding.collectionsList.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.collectionsList.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    viewModel.loadCollections()
                }
            }
        } else {
            binding.collectionsList.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
