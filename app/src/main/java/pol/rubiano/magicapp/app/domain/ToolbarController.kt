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
import pol.rubiano.magicapp.features.domain.models.Deck
import pol.rubiano.magicapp.features.presentation.ui.SearchFragment
import pol.rubiano.magicapp.features.presentation.ui.decks.DeckDetailsFragmentDirections
import pol.rubiano.magicapp.features.presentation.ui.decks.EditDeckFragment
import pol.rubiano.magicapp.features.presentation.ui.decks.NewDeckFragment


class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val bottomNav: BottomNavigationView,
    private val navController: NavController
) {

    private val topLevelDestinations = setOf(
        R.id.magicFragment,
        R.id.collectionsFragment,
        R.id.randomCardFragment,
    )
    private val secondaryDestinations = setOf(
        R.id.decksFragment,
        R.id.searchFragment
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

        if (destination.id in topLevelDestinations) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toolbar.setNavigationOnClickListener(null)
            toolbar.isTitleCentered = true
            bottomNav.visibility = View.VISIBLE
        } else if (destination.id in secondaryDestinations) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
            toolbar.isTitleCentered = false
            bottomNav.visibility = View.VISIBLE
        } else {
            bottomNav.visibility = View.GONE
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
                            navController.navigate(R.id.action_decksFragment_to_newDeckFragment)
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
            }
            R.id.deckDetailsFragment -> {
                prepareToolbar(R.menu.deck_config_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.cfg_deck_add_card -> {
                            val deck =
                                navController.currentBackStackEntry?.arguments?.getParcelable(
                                        "deck",
                                        Deck::class.java
                                    )
                            if (deck != null) {
                                val direction =
                                    DeckDetailsFragmentDirections.actionDeckDetailsFragmentToSearchFragment(
                                        deck
                                    )
                                navController.navigate(direction)
                            }
                            true
                        }

                        R.id.cfg_deck_edit -> {
                            val deck =
                                navController.currentBackStackEntry?.arguments?.getParcelable(
                                        "deck",
                                        Deck::class.java
                                    )
                            if (deck != null) {
                                val direction =
                                    DeckDetailsFragmentDirections.actionDeckDetailsFragmentToDeckEditFragment(
                                            deck
                                        )
                                navController.navigate(direction)
                            }
                            true
                        }

                        else -> false
                    }
                }
            }
            R.id.editDeckFragment -> {
                prepareToolbar(R.menu.deck_edit_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.edit_deck_save -> {
                            val navHostFragment =
                                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val currentFragment =
                                navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
                            if (currentFragment is EditDeckFragment) {
                                currentFragment.saveDeckChanges()
                            }
                            true
                        }

                        else -> false
                    }
                }
            }
            R.id.newDeckFragment -> {
                prepareToolbar(R.menu.new_deck_menu)
                toolbar.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.btnSaveNewDeck -> {
                            val navHostFragment =
                                activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                            val currentFragment =
                                navHostFragment?.childFragmentManager?.fragments?.firstOrNull()
                            if (currentFragment is NewDeckFragment) {
                                currentFragment.newDeck()
                            }
                            true
                        }

                        else -> false
                    }
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
}
