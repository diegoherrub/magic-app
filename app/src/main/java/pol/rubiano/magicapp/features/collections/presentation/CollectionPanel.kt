package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.CollectionPanelBinding
import pol.rubiano.magicapp.features.collections.presentation.adapters.CardsInCollectionAdapter
import pol.rubiano.magicapp.features.collections.presentation.adapters.CollectionsViewModel

class CollectionPanel : Fragment() {

    private var _bindingCollectionPanel : CollectionPanelBinding? = null
    private val bindinCollectionPanel get() = _bindingCollectionPanel!!
    private val collectionsViewModel: CollectionsViewModel by viewModel()

    private lateinit var adapterCardsInCollectionAdapter: CardsInCollectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingCollectionPanel = CollectionPanelBinding.inflate(inflater, container, false)
        return bindinCollectionPanel.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterCardsInCollectionAdapter = CardsInCollectionAdapter()

        val collectionName = CollectionPanelArgs.fromBundle((requireArguments())).collectionName

        bindinCollectionPanel.collectionPanelCards.layoutManager = LinearLayoutManager(requireContext())
        bindinCollectionPanel.collectionPanelCards.adapter = adapterCardsInCollectionAdapter

        setupObservers()
    }

    private fun setupObservers() {
        collectionsViewModel.currentCollection.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val collection = state.data
                    Log.d("@pol", "CollectionPanel -> collection $collection")
                    adapterCardsInCollectionAdapter.submitList(collection.cards)

                }
                else -> {}
            }
        }
    }
}