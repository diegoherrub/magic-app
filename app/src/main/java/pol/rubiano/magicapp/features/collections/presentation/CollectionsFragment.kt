package pol.rubiano.magicapp.features.collections.presentation

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.R
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.domain.UiState
import pol.rubiano.magicapp.databinding.CollectionsFragmentBinding
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toDrawable

class CollectionsFragment : Fragment() {

    private var _binding: CollectionsFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CollectionsViewModel by viewModel()
    private val collectionsAdapter = CollectionsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("@pol", "Entra en CollectionsFragment.onViewCreated()")
        super.onViewCreated(view, savedInstanceState)
        binding.collectionsList.layoutManager = LinearLayoutManager(requireContext())
        binding.collectionsList.addItemDecoration(
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        )
        binding.collectionsList.adapter = collectionsAdapter

        // Start config swipe

        // End config swipe

        Log.d(
            "@pol",
            "start -> CollectionsFragment.setupObservers().viewModel.loadUserCollections()"
        )
        viewModel.loadCollections()
        Log.d("@pol", "end -> CollectionsFragment.setupObservers().viewModel.loadUserCollections()")

        Log.d("@pol", "start -> CollectionsFragment.onViewCreated().setupObservers")
        setupObservers()
        Log.d("@pol", "end -> CollectionsFragment.onViewCreated().setupObservers")

        Log.d("@pol", "Se supone que viewModel ya va cargado con los succes")
        // TODO comprobar que el viewModel está relleno con pasta fresca

        Log.d("@pol", "start -> Asignamos navegación al botón new collection")
        binding.btnNewCollection.setOnClickListener {
            findNavController().navigate(R.id.action_collectionsFragment_to_newCollectionFragment)
        }
    }

    private fun setupObservers() {
        Log.d("@pol", "CollectionsFragment.setupObservers()")
        viewModel.userCollections.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding.collectionsList.visibility = View.VISIBLE

                    Log.d(
                        "@pol",
                        "Entra en CollectionsFragment.setupObservers().Uisatate.Success()"
                    )
                    val collections = state.data
                    Log.d("@pol", "Collections: $collections")

                    Log.d(
                        "@pol",
                        "start -> CollectionsFragment.setupObservers().Uisatate.Success().submitList"
                    )
                    collectionsAdapter.submitList(collections)

                    Log.d(
                        "@pol",
                        "end -> CollectionsFragment.setupObservers().Uisatate.Success().submitList"
                    )
                }
                // TODO - hacer mensaje con collection vacias?
                // TODO - implementar un primero botón para añadir una?
                // TODO - solo dejar el icono de la toolbar para añadir?
                is UiState.Empty -> {
                    Log.d("@pol", "Start -> CollectionsFragment.setupObservers().Uisatate.Empty()")
                    binding.collectionsList.visibility = View.GONE
                }

                is UiState.Error -> {
                    Log.d("@pol", "Entra en CollectionsFragment.setupObservers().Uisatate.Error()")
                    AppError.AppDataError
                }

                else -> {
                    Log.d(
                        "@pol",
                        "Start/End -> CollectionsFragment.setupObservers().Uisatate.Else()"
                    )
                }
            }
        }
        Log.d("@pol", "End -> CollectionsFragment.setupObservers")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
