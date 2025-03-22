package pol.rubiano.magicapp.features.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.faltenreich.skeletonlayout.SkeletonLayout
//import com.faltenreich.skeletonlayout.createSkeleton
import pol.rubiano.magicapp.databinding.RandomCardFragmentBinding
import pol.rubiano.magicapp.features.presentation.viewmodels.RandomCardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject
//import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.presentation.adapters.CardBindingHandler
import pol.rubiano.magicapp.app.common.extensions.gone
import pol.rubiano.magicapp.app.domain.ErrorApp
import pol.rubiano.magicapp.app.domain.toLegalityItemList
import pol.rubiano.magicapp.app.presentation.error.ErrorAppUIFactory
import pol.rubiano.magicapp.app.presentation.legalities.LegalitiesAdapter

class RandomCardFragment : Fragment() {

    private var _binding: RandomCardFragmentBinding? = null
    private val binding get() = _binding!!
    private val cardBinder = CardBindingHandler()
    private val viewModel: RandomCardViewModel by viewModel()
    private val errorFactory: ErrorAppUIFactory by inject()

//    private lateinit var skeleton: SkeletonLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = RandomCardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //skeleton = binding.root.findViewById(R.id.random_card_skeleton)
//        skeleton = view.createSkeleton()
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
//            checkLoading(uiState.isLoading)
            bindError(uiState.errorApp)
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

//    private fun checkLoading(isLoading: Boolean) {
//        if (isLoading) {
//            skeleton.showSkeleton()
//        } else {
//            skeleton.showOriginal()
//        }
//    }

    private fun bindError(errorApp: ErrorApp?) {
        errorApp?.let {
            binding.errorApp.render(
                errorFactory.build(it)
            )
        } ?: run {
            binding.errorApp.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}