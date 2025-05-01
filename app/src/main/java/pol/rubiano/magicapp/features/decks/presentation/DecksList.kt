package pol.rubiano.magicapp.features.decks.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
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
import pol.rubiano.magicapp.databinding.DecksListBinding
import pol.rubiano.magicapp.features.decks.presentation.assets.DecksAdapter
import pol.rubiano.magicapp.features.decks.presentation.assets.DecksViewModel

class DecksList : Fragment() {

    private var _binding: DecksListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DecksViewModel by viewModel()
    private val adapter = DecksAdapter { deck ->
        val passDeckClickedAction = DecksListDirections.actFromDecksListToDeckPanel(deck.id)
        findNavController().navigate(passDeckClickedAction)
    }
    private val errorFactory: AppErrorUIFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DecksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupView()
        setupObserver()
    }

    private fun setupToolbar() {
        val toolbar = requireActivity().findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.itm_addDeck -> {
                    findNavController().navigate(
                        DecksListDirections.actFromDecksListToNewDeck()
                    )
                    true
                }
                else -> false
            }
        }
    }

    private fun setupView() {
        binding.decksList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@DecksList.adapter
        }
        viewModel.loadDecks()
    }

    private fun setupObserver() {
        viewModel.fetchedDecks.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.decksList.visibility = View.VISIBLE
                    val decks = state.data
                    adapter.submitList(decks)
                }

                is UiState.Empty -> {
                    Toast.makeText(
                        requireContext(),
                        R.string.str_noDecksYet,
                        Toast.LENGTH_SHORT
                    ).show()
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
            binding.decksList.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.decksList.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    viewModel.loadDecks()
                }
            }
        } else {
            binding.decksList.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

