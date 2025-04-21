package pol.rubiano.magicapp.features.collections.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import pol.rubiano.magicapp.databinding.NewCollectionFormBinding
import pol.rubiano.magicapp.features.collections.presentation.assets.CollectionsViewModel

class NewCollectionForm : Fragment() {

    private var _binding: NewCollectionFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()

    private var newCollectionName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NewCollectionFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObserver()
    }

    private fun setupView() {
        binding.btnCreateNewCollection.setOnClickListener {
            newCollectionName = binding.newCollectionName.text.toString().trim()
            if (newCollectionName.isEmpty()) newCollectionName =
                getString(R.string.str_newCollectionTitle)
            viewModel.createCollection(newCollectionName)
        }
    }

    private fun setupObserver() {
        viewModel.currentCollection.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val actionWhenSaved =
                        NewCollectionFormDirections.actFromNewCollectionFormToCollectionsList()
                    findNavController().navigate(actionWhenSaved)
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
            binding.newCollectionForm.gone()
            binding.appErrorViewContainer.visible()
            binding.appErrorViewContainer.render(errorFactory.build(appError))
            binding.appErrorViewContainer.setOnRetryClickListener {
                binding.appErrorViewContainer.gone()
                binding.newCollectionForm.visible()
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(2000)
                    viewModel.createCollection(newCollectionName)
                }
            }
        } else {
            binding.newCollectionForm.visible()
            binding.appErrorViewContainer.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

