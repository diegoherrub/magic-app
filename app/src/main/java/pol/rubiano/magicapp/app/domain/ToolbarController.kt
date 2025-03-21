package pol.rubiano.magicapp.app.domain

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.navOptions
import com.google.android.material.appbar.MaterialToolbar
import pol.rubiano.magicapp.R
import androidx.core.view.size
import androidx.core.view.get

class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val navController: NavController
) {

    private val topLevelDestinations = setOf(
        R.id.magicFragment,
        R.id.collectionsFragment,
        R.id.decksFragment,
        R.id.randomCardFragment,
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

        if (destination.id in topLevelDestinations) {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toolbar.setNavigationOnClickListener(null)
        } else {
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { navController.navigateUp() }
        }

        toolbar.navigationIcon?.setTint(ContextCompat.getColor(activity, R.color.md_theme_onPrimary))

        if (destination.id == R.id.randomCardFragment) {
            toolbar.menu.clear()
            toolbar.inflateMenu(R.menu.random_card_menu)
            for (i in 0 until toolbar.menu.size) {
                toolbar.menu[i].icon?.setTint(ContextCompat.getColor(activity, R.color.md_theme_onPrimary))
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
        } else {
            toolbar.menu.clear()
            toolbar.setOnMenuItemClickListener(null)
        }
    }
}
