package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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

    private lateinit var toolbar: MaterialToolbar
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
        toolbar = requireActivity().findViewById(R.id.toolbar)

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
        val layoutManager = GridLayoutManager(requireContext(), 4)
        binding.collectionPanelCards.apply {
            this.layoutManager = layoutManager
            adapter = this@CollectionPanel.adapter
        }
        viewModel.loadCollection(collectionName)
    }

    //private fun calculateSpanCount(): Int {
    //    // Calcula el número de columnas basado en el ancho de pantalla
    //    val displayMetrics = DisplayMetrics()
    //    requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
    //    val widthPixels = displayMetrics.widthPixels
    //    val cardWidth = resources.getDimension(R.dimen.card_item_width) // Define este valor en dimens.xml
    //    return (widthPixels / cardWidth).coerceAtLeast(1)
    //}

    private fun setupObservers() {
        viewModel.currentCollection.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val collection = state.data
                    Log.d("@pol", "CollectionPanel.setupObservers -> list cardsInCollection -> ")
                    adapter.submitList(collection.cards)
                }

                is UiState.Error -> bindError(state.error)

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
                    viewModel.loadCollection(collectionName)
                }
            }
        } else {
            binding.collectionPanelCards.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCollection(collectionName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}