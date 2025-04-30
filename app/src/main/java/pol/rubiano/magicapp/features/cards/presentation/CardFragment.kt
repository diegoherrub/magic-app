package pol.rubiano.magicapp.features.cards.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.common.utils.CardEffects
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.databinding.CardFragmentViewBinding
import pol.rubiano.magicapp.features.cards.domain.models.Card
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsViewModel

class CardFragment : Fragment() {

    private var _binding: CardFragmentViewBinding? = null
    private val binding get() = _binding!!
    private val cardBinder = CardBindingHandler()
    private val cardViewModel: CardViewModel by viewModel()
    private val collectionsViewModel: CollectionsViewModel by viewModel()
    private val cardFragmentArgs: CardFragmentArgs by navArgs()
    private val errorFactory: AppErrorUIFactory by inject()

    private lateinit var card: Card
    private var deckId: String? = null
    private var collectionName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = CardFragmentViewBinding.inflate(inflater, container, false)
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
        cardFragmentArgs.collectionName?.let { collectionName = it }
        cardFragmentArgs.card.let { card = it }
        card = cardFragmentArgs.card
        toolbar.title = card.name
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        if (!collectionName.isNullOrEmpty()) {
            toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.itm_add_card -> {
                        if (collectionName != null) {
                            cardViewModel.saveCard(card)
                            collectionsViewModel.saveCardToCollection(card.id, collectionName!!)

                            Toast.makeText(
                                requireContext(),
                                R.string.str_addedCardToCollection,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setupView() {
        card.let {
            cardBinder.bind(it, binding)
        }
        cardBinder.setupLegalitiesRecyclerView(binding.legalitiesList)
        binding.viewCardImage.post {
            CardEffects.configureCardImageView(
                binding.viewCardImage,
                resources.displayMetrics.density
            )
        }
        binding.flipButton.setOnClickListener {
            CardEffects.flip(binding.viewCardImage) {
                cardBinder.flipCard(binding)
            }
        }
    }

    private fun setupObservers() {
        cardViewModel.card.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    cardBinder.bind(state.data, binding)
                }

                is UiState.Error -> {
                    bindError(state.error)
                }

                else -> {}
            }
        }
    }

    private fun bindError(appError: AppError?) {
        appError?.let {
            binding.viewCardContainer.visibility = View.GONE
            binding.appErrorViewContainer.visibility = View.VISIBLE
            binding.appErrorViewContainer.render(errorFactory.build(it))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.visibility = View.GONE
                binding.viewCardContainer.visibility = View.VISIBLE
            }
        } ?: run {
            binding.viewCardContainer.visibility = View.VISIBLE
            binding.appErrorViewContainer.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}