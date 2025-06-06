package pol.rubiano.magicapp.app.domain

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.appbar.MaterialToolbar
import pol.rubiano.magicapp.R
import androidx.core.view.size
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pol.rubiano.magicapp.features.decks.presentation.DeckPanelDirections
import pol.rubiano.magicapp.features.decks.presentation.DecksListDirections
import pol.rubiano.magicapp.features.decks.presentation.NewDeckFormDirections
import pol.rubiano.magicapp.features.search.presentation.SearchFragmentDirections

class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val bottomNav: BottomNavigationView,
    private val navController: NavController
) {

    private val topLevelDestinations = setOf(
        R.id.magicFragment,
        R.id.collectionsList,
        R.id.randomCardFragment,
        R.id.decksList,
    )

    private val secondaryDestinations = setOf(
        R.id.legalFormats,
        R.id.keywords,
        R.id.collectionPanel,
        R.id.newCollectionForm,
        R.id.newDeck,
        R.id.deckPanel,
    )

    private val specialDestinations = setOf(
        R.id.searchFragment,
        R.id.resultsFragment,
        R.id.cardFragment,
    )

    init {
        activity.setSupportActionBar(toolbar)
        configureToolbar()
    }

    private fun configureToolbar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbar(destination)
        }
        navController.currentDestination?.let { updateToolbar(it) }
    }

    private fun updateToolbar(destination: NavDestination) {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        toolbar.setNavigationOnClickListener(null)
        toolbar.title = destination.label
        val args = navController.currentBackStackEntry?.arguments

        when (destination.id) {
            in topLevelDestinations -> {
                toolbar.isTitleCentered = true
                bottomNav.visibility = View.VISIBLE

            }

            in secondaryDestinations -> {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.navigationIcon.apply { R.drawable.back }
                toolbar.isTitleCentered = false
                bottomNav.visibility = View.VISIBLE
            }

            in specialDestinations -> {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.navigationIcon.apply { R.drawable.back }
                toolbar.isTitleCentered = true
                bottomNav.visibility = View.GONE
            }

            else -> {}
        }

        toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(activity, R.color.md_theme_onPrimary)
        )

        when (destination.id) {

            R.id.keywords -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromKeywords_toMagic)
                }
            }

            R.id.legalFormats -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromLegalFormats_toMagic)
                }
            }

            R.id.collectionsList -> prepareToolbar(R.menu.collections_list_menu)

            R.id.collectionPanel -> {
                prepareToolbar(R.menu.collection_panel_menu)
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromCollectionPanel_toCollectionsList)
                }
            }

            R.id.newCollectionForm -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromNewCollectionForm_toCollectionsList)
                }
            }

            R.id.decksList -> prepareToolbar(R.menu.md_decks_list)

            R.id.newDeck -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromNewDeck_toDecksList)
                }
            }

            R.id.deckPanel -> prepareToolbar(R.menu.mn_deck_panel)

            R.id.randomCardFragment -> prepareToolbar(R.menu.random_card_menu)

            R.id.searchFragment -> {
                prepareToolbar(R.menu.search_menu)
                val collectionName = args?.getString("collectionname")
                val deckId = args?.getString("deckId")

                if (collectionName != null) {
                    setCustomNavigationAction {
                        val action = SearchFragmentDirections.actFromSearchFragmentToCollectionPanel(collectionName)
                        navController.navigate(action)
                    }
                } else if(deckId != null) {
                    setCustomNavigationAction {
                        val action = SearchFragmentDirections.actFromSearchFragmentToDeckDetailsFragment(deckId)
                        navController.navigate(action)
                    }
                }  else {
                    setCustomNavigationAction {
                        navController.popBackStack()
                    }
                }
            }

            R.id.cardFragment -> {
                prepareToolbar(R.menu.mn_card)
                setCustomNavigationAction {
                    navController.popBackStack()
                }
            }

            else -> {
                toolbar.menu.clear()
                toolbar.setOnMenuItemClickListener(null)
            }
        }
    }

    private fun prepareToolbar(menuId: Int) {
        toolbar.menu.clear()
        toolbar.inflateMenu(menuId)
        for (i in 0 until toolbar.menu.size) {
            toolbar.menu[i].icon?.setTint(
                ContextCompat.getColor(activity, R.color.md_theme_onPrimary)
            )
        }
    }

    private fun setCustomNavigationAction(action: () -> Unit) {
        toolbar.setNavigationOnClickListener { action() }
    }
}
