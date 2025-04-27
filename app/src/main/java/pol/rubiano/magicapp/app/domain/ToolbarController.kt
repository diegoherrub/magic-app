package pol.rubiano.magicapp.app.domain

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.navOptions
import com.google.android.material.appbar.MaterialToolbar
import pol.rubiano.magicapp.R
import androidx.core.view.size
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pol.rubiano.magicapp.features.decks.domain.models.Deck
import pol.rubiano.magicapp.features.decks.presentation.DeckPanelDirections

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
        R.id.searchFragment,
    )

    private val secondaryDestinations = setOf(
        R.id.legalFormats,
        R.id.keywords,
        R.id.collectionPanel,
        R.id.newCollectionForm,
        R.id.newDeck,
        R.id.deckPanel,



        R.id.cardFragmentView,
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
        bottomNav.visibility = View.VISIBLE

        when (destination.id) {
            in topLevelDestinations -> toolbar.isTitleCentered = true

            in secondaryDestinations -> {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.isTitleCentered = false
            }

            else -> { }
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
            } // REVISADO

            R.id.legalFormats -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromLegalFormats_toMagic)
                }
            } // REVISADO

            R.id.collectionsList -> {
                prepareToolbar(R.menu.collections_list_menu)
            } // REVISADO

            R.id.collectionPanel -> {
                prepareToolbar(R.menu.collection_panel_menu)
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromCollectionPanel_toCollectionsList)
                }
            } // REVISADO

            R.id.newCollectionForm -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromNewCollectionForm_toCollectionsList)
                }
            } // REVISADO

            R.id.decksList -> {
                prepareToolbar(R.menu.decks_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_deck -> {
                            navController.navigate(R.id.act_fromDecksList_toNewDeckFragment)
                            true
                        }

                        else -> false
                    }
                }
            } // REVISADO

            R.id.newDeck -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromNewDeck_toDecksList)
                }
            } // REVISADO

            R.id.deckPanel -> {
                prepareToolbar(R.menu.mn_deck_panel)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.itm_add_card_to_deck -> {
                            TODO()
                            true
                        }
                        R.id.itm_edit_deck -> {
                            TODO()
                            true
                        }
                        else -> false
                    }
                }
                setCustomNavigationAction {
                    navController.navigate(DeckPanelDirections.actFromDeckPanelToDecksList())
                }
            }





            


            R.id.randomCardFragment -> {
                prepareToolbar(R.menu.random_card_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_refresh -> {
                            val options = navOptions {
                                popUpTo(R.id.randomCardFragment) { inclusive = true }
                            }
                            navController.navigate(R.id.randomCardFragment, null, options)
                            true
                        }

                        else -> false
                    }
                }
            }

            R.id.searchFragment -> prepareToolbar(R.menu.search_menu)

            R.id.cardFragmentView -> {
                toolbar.menu.clear()
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
