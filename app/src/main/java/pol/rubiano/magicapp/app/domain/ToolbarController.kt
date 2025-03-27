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
import pol.rubiano.magicapp.features.presentation.ui.SearchFragment

class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val bottomNav: BottomNavigationView,
    private val navController: NavController
) {

    private val topLevelDestinations = setOf(
        R.id.magicFragment,
        R.id.collectionsFragment,
        R.id.decksFragment,
        R.id.randomCardFragment,
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
        } else {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
            toolbar.isTitleCentered = false
            bottomNav.visibility = View.GONE
        }

        toolbar.navigationIcon?.setTint(
            ContextCompat.getColor(
                activity,
                R.color.md_theme_onPrimary
            )
        )

        when (destination.id) {
            R.id.randomCardFragment -> {
                toolbar.menu.clear()
                toolbar.inflateMenu(R.menu.random_card_menu)
                for (i in 0 until toolbar.menu.size) {
                    toolbar.menu[i].icon?.setTint(
                        ContextCompat.getColor(
                            activity,
                            R.color.md_theme_onPrimary
                        )
                    )
                }
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
                toolbar.menu.clear()
                toolbar.inflateMenu(R.menu.search_menu)
                for (i in 0 until toolbar.menu.size) {
                    toolbar.menu[i].icon?.setTint(
                        ContextCompat.getColor(
                            activity,
                            R.color.md_theme_onPrimary
                        )
                    )
                }
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
            else -> {
                toolbar.menu.clear()
                toolbar.setOnMenuItemClickListener(null)
            }
        }
    }
}
