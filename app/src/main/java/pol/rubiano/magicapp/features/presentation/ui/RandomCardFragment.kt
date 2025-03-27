package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pol.rubiano.magicapp.databinding.RandomCardFragmentBinding
import pol.rubiano.magicapp.features.presentation.viewmodels.RandomCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.adapters.CardBindingHandler
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.common.extensions.visible
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.toLegalityItemList
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import pol.rubiano.magicapp.app.presentation.legalities.LegalitiesAdapter

class RandomCardFragment : Fragment() {

    private var _binding: RandomCardFragmentBinding? = null
    private val binding get() = _binding!!
    private val cardBinder = CardBindingHandler()
    private val viewModel: RandomCardViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = RandomCardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLegalitiesRecyclerView()
        setupObserver()
        if (savedInstanceState == null && viewModel.uiState.value?.card == null) {
            viewModel.fetchRandomCard()
        }
        binding.randomCardImage.post {
            val density = resources.displayMetrics.density
            binding.randomCardImage.pivotX = binding.randomCardImage.width / 2f
            binding.randomCardImage.pivotY = binding.randomCardImage.height / 2f
            binding.randomCardImage.cameraDistance = 8000 * density
        }
        binding.flipButton.setOnClickListener {
            binding.randomCardImage.animate().rotationY(90f).setDuration(300)
                .withEndAction {
                    cardBinder.flipCard(binding)
                    binding.randomCardImage.rotationY = -90f
                    binding.randomCardImage.animate().rotationY(0f).setDuration(300).start()
                }.start()
        }

    }

    private fun setupLegalitiesRecyclerView() {
        val spanCount = 2
        val orientation = StaggeredGridLayoutManager.VERTICAL
        val layoutManager = StaggeredGridLayoutManager(spanCount, orientation)

        binding.legalitiesList.layoutManager = layoutManager
        binding.legalitiesList.adapter = LegalitiesAdapter()
    }

    private fun setupObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            bindError(uiState.appError)
            uiState.card?.let { card ->
                cardBinder.bind(card, binding)

                card.legalities?.let { legalities ->
                    val legalityItems = legalities.toLegalityItemList()
                    (binding.legalitiesList.adapter as LegalitiesAdapter).submitList(legalityItems)
                }
                if (card.frontFace != null && card.backFace != null) {
                    binding.flipButton.visibility = View.VISIBLE
                } else {
                    binding.flipButton.visibility = View.GONE
                }
            }
        }
    }

    private fun bindError(appError: AppError?) {
        appError?.let {
            binding.randomCardContainer.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(it))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.randomCardContainer.gone()

                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    viewModel.fetchRandomCard()
                }
            }
        } ?: run {
            binding.randomCardContainer.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}