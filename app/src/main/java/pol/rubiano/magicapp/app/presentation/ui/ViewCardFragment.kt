//package pol.rubiano.magicapp.app.presentation.ui
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import org.koin.android.ext.android.inject
//import pol.rubiano.magicapp.app.presentation.viewmodels.ViewCardViewModel
//import pol.rubiano.magicapp.databinding.ViewCardFragmentBinding
//import org.koin.androidx.viewmodel.ext.android.viewModel
//import pol.rubiano.magicapp.app.common.CardEffects
//import pol.rubiano.magicapp.app.domain.AppError
//import pol.rubiano.magicapp.app.presentation.adapters.CardBindingHandler
//import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
//import androidx.lifecycle.observe
//
//class ViewCardFragment : Fragment() {
//
//    private var _binding: ViewCardFragmentBinding? = null
//    private val binding get() = _binding!!
//    private val cardBinder = CardBindingHandler()
//    private val viewModel: ViewCardViewModel by viewModel()
//    private val errorFactory: AppErrorUIFactory by inject()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View {
//        _binding = ViewCardFragmentBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupView()
//        setupObservers()
////        if (savedInstanceState == null) viewModel.loadCard()
//        val args = ViewCardFragmentArgs.fromBundle(requireArguments())
//        viewModel.setCard(args.card)
//    }
//
//    private fun setupView() {
////        CardEffects.configureCardImageView(binding.viewCardImage, resources.displayMetrics.density)
////        CardEffects.flip(binding.viewCardImage)
//    }
//
//    private fun setupObservers() {
//        viewModel.card.observe(viewLifecycleOwner) { card ->
//            // Utilizamos el CardBindingHandler para asignar los datos de la carta a la vista
//            cardBinder.bind(card, binding)
//        }
//
//        viewModel.uiError.observe(viewLifecycleOwner) { appError ->
//            bindError(appError)
//        }
//    }
//
//    private fun bindError(appError: AppError?) {
//        appError?.let {
//            binding.viewCardContainer.visibility = View.GONE
//            binding.appErrorViewContainer.visibility = View.VISIBLE
//            binding.appErrorViewContainer.render(errorFactory.build(it))
//            binding.appErrorViewContainer.setOnRetryClickListener {
//                binding.appErrorViewContainer.visibility = View.GONE
//                binding.viewCardContainer.visibility = View.VISIBLE
////                viewModel.loadCard()
//            }
//        } ?: run {
//            binding.viewCardContainer.visibility = View.VISIBLE
//            binding.appErrorViewContainer.visibility = View.GONE
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

package pol.rubiano.magicapp.app.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import pol.rubiano.magicapp.app.presentation.viewmodels.ViewCardViewModel
import pol.rubiano.magicapp.databinding.ViewCardFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import pol.rubiano.magicapp.app.common.CardEffects
import pol.rubiano.magicapp.app.domain.AppError
import pol.rubiano.magicapp.app.presentation.adapters.CardBindingHandler
import pol.rubiano.magicapp.app.presentation.error.AppErrorUIFactory
import androidx.lifecycle.observe
import com.google.gson.Gson
import pol.rubiano.magicapp.app.domain.Card

class ViewCardFragment : Fragment() {

    private var _binding: ViewCardFragmentBinding? = null
    private val binding get() = _binding!!
    private val cardBinder = CardBindingHandler()
    private val viewModel: ViewCardViewModel by viewModel()
    private val errorFactory: AppErrorUIFactory by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = ViewCardFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
//        if (savedInstanceState == null) viewModel.loadCard()
        val args = ViewCardFragmentArgs.fromBundle(requireArguments())
        val cardJson = args.cardJson
        val card = Gson().fromJson(cardJson, Card::class.java)
        viewModel.setCard(card)
    }

    private fun setupView() {
        cardBinder.setupLegalitiesRecyclerView(binding.legalitiesList)
        binding.viewCardImage.post {
            CardEffects.configureCardImageView(binding.viewCardImage, resources.displayMetrics.density)
        }
        binding.flipButton.setOnClickListener {
            CardEffects.flip(binding.viewCardImage) {
                cardBinder.flipCard(binding)
            }
        }
    }

    private fun setupObservers() {
        viewModel.card.observe(viewLifecycleOwner) { card: Card ->
            // Utilizamos el CardBindingHandler para asignar los datos de la carta a la vista
            cardBinder.bind(card, binding)
        }

        viewModel.uiError.observe(viewLifecycleOwner) { appError: AppError? ->
            bindError(appError)
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
//                viewModel.loadCard()
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