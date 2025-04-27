package pol.rubiano.magicapp.features.decks.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import pol.rubiano.magicapp.databinding.NewDeckFormBinding
import pol.rubiano.magicapp.features.decks.presentation.assets.DecksViewModel

class NewDeckForm : Fragment() {

    private var _binding: NewDeckFormBinding? = null
    private val binding get() = _binding!!
    private val decksViewModel: DecksViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()

    private var newDeckName: String = ""
    private var newDeckDescription: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewDeckFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        binding.btnCreateNewDeck.setOnClickListener {
            newDeckName = binding.newDeckName.text.toString().trim()
            newDeckDescription = binding.newDeckDescription.text.toString().trim()
            if (newDeckName.isEmpty()) newDeckName = getString(R.string.str_newDeck)
            decksViewModel.createNewDeck(newDeckName, newDeckDescription)
        }
    }

    private fun setupObserver() {
        decksViewModel.newDeckCreated.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    val deck = state.data
                    findNavController().navigate(
                        NewDeckFormDirections.actFromNewDeckToDeckPanel(
                            deck.id
                        )
                    )
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
            binding.newDeckForm.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.newDeckForm.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    decksViewModel.createNewDeck(newDeckName, newDeckDescription)
                }
            }
        } else {
            binding.newDeckForm.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

