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
import com.google.android.material.bottomnavigation.BottomNavigationView
import pol.rubiano.magicapp.features.collections.presentation.CollectionPanelDirections
import pol.rubiano.magicapp.features.collections.presentation.CollectionsListDirections
import pol.rubiano.magicapp.features.decks.deckdetails.DeckDetailsFragmentDirections
import pol.rubiano.magicapp.features.search.SearchFragment
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.search.SearchFragmentDirections


class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val bottomNav: BottomNavigationView,
    private val navController: NavController
) {

    private val topLevelDestinations = setOf(
        R.id.magicFragment,
    )

    private val secondaryDestinations = setOf(
        R.id.legalities_fragment,
        R.id.keywords_fragment,
        R.id.decksFragment,
        R.id.deckDetails,
        R.id.randomCardFragment,
        R.id.searchFragment,
        R.id.newCollectionForm,
        R.id.collectionPanel,
    )

    private val specialDestinations = setOf(
        R.id.collectionsList,
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

        toolbar.title = destination.label

        when (destination.id) {
            in topLevelDestinations -> {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toolbar.setNavigationOnClickListener(null)
                toolbar.isTitleCentered = true
                bottomNav.visibility = View.VISIBLE
            }

            in secondaryDestinations -> {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.isTitleCentered = false
                bottomNav.visibility = View.VISIBLE
            }

            in specialDestinations -> {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toolbar.setNavigationOnClickListener(null)
                toolbar.isTitleCentered = false
                bottomNav.visibility = View.VISIBLE
            }

            else -> {
                bottomNav.visibility = View.GONE
            }
        }

        toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(activity, R.color.md_theme_onPrimary)
        )

        when (destination.id) {
            R.id.decksFragment -> {
                prepareToolbar(R.menu.decks_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_deck -> {
                            navController.navigate(R.id.act_decks_to_new_deck)
                            true
                        }

                        else -> false
                    }
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

            R.id.searchFragment -> {
                prepareToolbar(R.menu.search_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.action_search -> {
                            val navHostFragment =
                                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val currentFragment =
                                navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
                            if (currentFragment is SearchFragment) {
                                currentFragment.performSearch()
                            }
                            true
                        }

                        else -> false
                    }
                }
                setCustomNavigationAction {
                    val deck = navController.currentBackStackEntry?.arguments?.getParcelable<Deck>("deck")
                    val collectionName = navController.currentBackStackEntry?.arguments?.getString("collectionName")

                    when {
                        deck != null -> {
                            val direction = SearchFragmentDirections.actionSearchFragmentToDeckDetails(deck)
                            navController.navigate(direction)
                        }
                        collectionName != null -> {
                            val direction = SearchFragmentDirections.actionSearchFragmentToCollectionPanel(collectionName)
                            navController.navigate(direction)
                        }
                        else -> {
                            navController.popBackStack() // Acción por defecto si ambos son null
                        }
                    }
                }
            }

            R.id.deckDetails -> {
                prepareToolbar(R.menu.deck_details)
                val customBack = R.id.decksFragment
                setCustomNavigationAction {
                    navController.navigate(customBack)
                }
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.act_details_to_decks -> {
                            val direction = DeckDetailsFragmentDirections.actDetailsToDecks()
                            navController.navigate(direction)
                            true
                        }

                        R.id.icon_add_card -> {
                            val deck =
                                navController.currentBackStackEntry?.arguments?.getParcelable(
                                    "deck",
                                    Deck::class.java
                                )
                            if (deck != null) {
                                val direction =
                                    DeckDetailsFragmentDirections.actDeckDetailsToSearch(
                                        collectionName = null,
                                        deck = deck
                                    )
                                navController.navigate(direction)
                            }
                            true
                        }

//                        R.id.cfg_deck_edit -> {
//                            val deck =
//                                navController.currentBackStackEntry?.arguments?.getParcelable(
//                                        "deck",
//                                        Deck::class.java
//                                    )
//                            if (deck != null) {
//                                val direction =
//                                    DeckDetailsFragmentDirections.actionDeckDetailsFragmentToDeckEditFragment(
//                                            deck
//                                        )
//                                navController.navigate(R.id.actionDeckDetailsFragmentToDeckEditFragment)
//                            }
//                            true
//                        }

                        else -> false
                    }
                }
            }

//            R.id.editDeckFragment -> {
//                prepareToolbar(R.menu.deck_edit_menu)
//                toolbar.setOnMenuItemClickListener { item ->
//                    when (item.itemId) {
//                        R.id.edit_deck_save -> {
//                            val navHostFragment =
//                                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
//                            val currentFragment =
//                                navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
//                            if (currentFragment is EditDeckFragment) {
//                                currentFragment.saveDeckChanges()
//                            }
//                            true
//                        }
//
//                        else -> false
//                    }
//                }
//            }

            R.id.collectionsList -> {
                prepareToolbar(R.menu.collections_list_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.item_menu_add_new_collection -> {
                            val direction =
                                CollectionsListDirections.actionCollectionsListToNewCollectionForm()
                            navController.navigate(direction)
                            true
                        }

                        else -> false
                    }
                }
            }

            R.id.collectionPanel -> {
                val collectionName =
                    navController.currentBackStackEntry?.arguments?.getString("collectionName")
                if (collectionName != null) toolbar.title = collectionName
                prepareToolbar(R.menu.collection_panel_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.itemMenuAddCardToCollection -> {
                            val direction =
                                CollectionPanelDirections.actionCollectionPanelToSearchFragment(
                                    collectionName = collectionName,
                                    deck = null
                                )
                            navController.navigate(direction)
                            true
                        }

                        else -> false
                    }
                }
                // Custom behaviour for the navigation back arrow
                setCustomNavigationAction {
                    val direction =
                        CollectionPanelDirections.actionCollectionPanelToCollectionsList()
                    navController.navigate(direction)
                }
            }

            R.id.newCollectionForm -> {
                prepareToolbar()
                setCustomNavigationAction {
                    navController.navigate(R.id.action_newCollectionForm_to_collectionsList)
                }
            }

            else -> {
                toolbar.menu.clear()
                toolbar.setOnMenuItemClickListener(null)
            }
        }
    }

    private fun prepareToolbar() {
        toolbar.menu.clear()
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
