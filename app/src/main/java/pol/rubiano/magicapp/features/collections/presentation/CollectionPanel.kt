package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.MainActivity
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.ToolbarController
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.CollectionPanelBinding
import pol.rubiano.magicapp.features.collections.presentation.assets.CardsInCollectionAdapter
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsViewModel

class CollectionPanel : Fragment() {

    private var _binding: CollectionPanelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()
    private val args: CollectionPanelArgs by navArgs()
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

//        if (resultsFragmentArgs.collectionName != null) {
//            collectionName = resultsFragmentArgs.collectionName!!
//            Log.d("@pol", "CollectionPanel.onViewCreated() recibe el nombre la colección desde results: $collectionName")
//        } else if (collectionPanelArgs.collectionName != null) {
//            collectionName = collectionPanelArgs.collectionName!!
//            Log.d("@pol", "CollectionPanel.onViewCreated() recibe el nombre la colección desde colection: $collectionName")
//        }

        Log.d("@pol", "start -> CollectionPanel.onViewCreated() -> loadCards")
        viewModel.loadCardsOfCollection(collectionName)
        Log.d("@pol", "end -> CollectionPanel.onViewCreated() -> loadCards")


        setupObservers()
    }

    private fun setupToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        collectionName = args.collectionName.toString()
        toolbar.title = collectionName
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.collection_panel_menu)
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
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(
                CollectionPanelDirections.actFromCollectionPanelToCollectionsList()
            )
        }
    }

    private fun setupView() {
        adapter = CardsInCollectionAdapter()
        binding.collectionPanelCards.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CollectionPanel.adapter
        }
    }

    private fun setupObservers() {
        viewModel.currentCollection.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val collection = state.data
                    Log.d("@pol", "CollectionPanel -> collection $collection")
                    adapter.submitList(collection.cards)
                }

                is UiState.Error -> {
                    Log.e("@pol", "Error al cargar las cartas de la colección")
                }

                else -> {}
            }
        }
    }
}