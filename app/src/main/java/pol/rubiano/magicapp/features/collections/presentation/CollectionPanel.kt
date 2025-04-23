package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
import pol.rubiano.magicapp.databinding.CollectionPanelBinding
import pol.rubiano.magicapp.features.collections.presentation.assets.CardsInCollectionAdapter
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsViewModel

class CollectionPanel : Fragment() {

    private var _binding: CollectionPanelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()
    private val collectionPanelArgs: CollectionPanelArgs by navArgs()
    private val errorFactory: AppErrorUIFactory by inject()

    private lateinit var adapter: CardsInCollectionAdapter
    private lateinit var collectionName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionPanelBinding.inflate(inflater, container, false)
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
        collectionPanelArgs.collectionName?.let { collectionName = it }
        toolbar.title = collectionName
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.itemMenuAddCardToCollection -> {
                    findNavController().navigate(
                        CollectionPanelDirections.actFromCollectionPanelToSearchFragment(
                            collectionName
                        )
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun setupView() {
        adapter = CardsInCollectionAdapter()
        binding.collectionPanelCards.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CollectionPanel.adapter
        }
        viewModel.loadCardsOfCollection(collectionName)
    }

    private fun setupObservers() {
        viewModel.currentCollection.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val collection = state.data
                    adapter.submitList(collection.cards)
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
            binding.collectionPanelCards.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.collectionPanelCards.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    viewModel.loadCardsOfCollection(collectionName)
                }
            }
        } else {
            binding.collectionPanelCards.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}