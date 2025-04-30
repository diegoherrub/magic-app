package pol.rubiano.magicapp.features.randomcard.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.features.cards.presentation.CardBindingHandler
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.common.utils.CardEffects
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.databinding.CardFragmentViewBinding

class RandomCardFragment : Fragment() {

    private var _binding: CardFragmentViewBinding? = null
    private val binding get() = _binding!!
    private val cardBinder = CardBindingHandler()
    private val randomCardViewModel: RandomCardViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()

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
        setupObserver()
        if (savedInstanceState == null && randomCardViewModel.randomCard.value == null) {
            randomCardViewModel.fetchRandomCard()
        }
    }

    private fun setupToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_refresh -> {
                    val options = navOptions {
                        popUpTo(R.id.randomCardFragment) { inclusive = true }
                    }
                    findNavController().navigate(R.id.randomCardFragment, null, options)
                    true
                }

                else -> false
            }
        }
    }

    private fun setupView() {
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

    private fun setupObserver() {
        randomCardViewModel.randomCard.observe(viewLifecycleOwner) { state ->
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
            binding.viewCardContainer.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(it))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.viewCardContainer.gone()

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    randomCardViewModel.fetchRandomCard()
                }
            }
        } ?: run {
            binding.viewCardContainer.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}