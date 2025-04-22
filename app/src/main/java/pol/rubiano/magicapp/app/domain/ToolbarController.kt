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
import pol.rubiano.magicapp.features.decks.deckdetails.DeckDetailsFragmentDirections
import pol.rubiano.magicapp.features.domain.models.Deck

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
        R.id.legalitiesFragment,
        R.id.keywordsFragment,
        R.id.decksFragment,
        R.id.deckDetailsFragment,
        R.id.randomCardFragment,
        R.id.newCollectionForm,
        R.id.collectionPanel,
        R.id.cardFragmentView,
    )

    private val specialDestinations = setOf(
        R.id.collectionsList,
        R.id.searchFragment,
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
                            navController.navigate(R.id.act_fromDecksFragment_toNewDeckFragment)
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

            R.id.searchFragment -> prepareToolbar(R.menu.search_menu)

            R.id.deckDetailsFragment -> {
                prepareToolbar(R.menu.deck_details)
                val customBack = R.id.decksFragment
                setCustomNavigationAction {
                    navController.navigate(customBack)
                }
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.act_fromDeckDetails_toDecksFragment -> {
                            val direction =
                                DeckDetailsFragmentDirections.actFromDeckDetailsToDecksFragment()
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
                                    DeckDetailsFragmentDirections.actFromDeckDetailsToSearchFragment(
//                                        collectionName = null,
//                                        deck = deck
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

            R.id.collectionPanel -> {
                prepareToolbar(R.menu.collection_panel_menu)
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromCollectionPanel_toCollectionsList)
                }
            }

            R.id.collectionsList -> {
                prepareToolbar(R.menu.collections_list_menu)
            }

            R.id.newCollectionForm -> {
                toolbar.menu.clear()
                setCustomNavigationAction {
                    navController.navigate(R.id.act_fromNewCollectionForm_toCollectionsList)
                }
            }

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
