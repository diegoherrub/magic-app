package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pol.rubiano.magicapp.features.presentation.viewmodels.RandomCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject
import pol.rubiano.magicapp.features.cards.presentation.CardBindingHandler
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.databinding.CardFragmentViewBinding

class RandomCardFragment : Fragment() {

    private var _binding: CardFragmentViewBinding? = null
    private val binding get() = _binding!!
    private val cardBinder = CardBindingHandler()
    private val viewModel: RandomCardViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = CardFragmentViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        if (savedInstanceState == null && viewModel.uiState.value?.card == null) {
            viewModel.fetchRandomCard()
        }
    }

    private fun setupObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            bindError(uiState.appError)
            uiState.card?.let { card ->
                cardBinder.bind(card, binding)
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
                    viewModel.fetchRandomCard()
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