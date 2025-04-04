//package pol.rubiano.magicapp.features.presentation.ui.decks
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.navArgs
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import pol.rubiano.magicapp.databinding.EditDeckFragmentBinding
//import pol.rubiano.magicapp.features.decks.DecksViewModel
//
//class EditDeckFragment : Fragment() {
//
//    private var _binding: EditDeckFragmentBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel: DecksViewModel by viewModel()
//    private val args: EditDeckFragmentArgs by navArgs()
////
////    private lateinit var currentDeck: Deck
////
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = EditDeckFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
////
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.loadCurrentDeck(args.deck)
////        setupObservers()
//    }
////
////    private fun setupObservers() {
////        viewModel.currentDeck.observe(viewLifecycleOwner) { state ->
////            when (state) {
////                is UiState.Success -> {
////                    currentDeck = state.data
////                    binding.editDeckName.setText(currentDeck.name)
////                    binding.editDeckDescription.setText(currentDeck.description)
////                }
////
////                is UiState.Loading -> {}
////                is UiState.Empty -> {}
////                is UiState.Error -> {}
////            }
////        }
////    }
////
////    fun saveDeckChanges() {
////        val updatedDeck = currentDeck.copy(
////            name = binding.editDeckName.text.toString(),
////            description = binding.editDeckDescription.text.toString()
////        )
////        viewModel.addDeck(updatedDeck)
////        val action = EditDeckFragmentDirections
////            .actionEditDeckFragmentToDeckConfigFragment(currentDeck)
////        findNavController().navigate(action)
////    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}