package pol.rubiano.magicapp.app.domain

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.navOptions
import com.google.android.material.appbar.MaterialToolbar
import pol.rubiano.magicapp.R

class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val navController: NavController
) {

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

//        if (destination.id != navController.graph.startDestinationId) {
//            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//            toolbar.setNavigationOnClickListener { navController.navigateUp() }
//        } else {
//            activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
//            toolbar.setNavigationOnClickListener(null)
//        }

        if (destination.id == R.id.randomCardFragment) {
            toolbar.menu.clear()
            toolbar.inflateMenu(R.menu.random_card_menu)
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
