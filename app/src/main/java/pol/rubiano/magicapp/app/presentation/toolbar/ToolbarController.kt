package pol.rubiano.magicapp.app.presentation.toolbar

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.google.android.material.appbar.MaterialToolbar
import pol.rubiano.magicapp.R

class ToolbarController(
    private val activity: AppCompatActivity,
    private val toolbar: MaterialToolbar,
    private val navController: NavController,

    ) {

    init {
        activity.setSupportActionBar(toolbar)
        configureToolbar()
    }

    private fun configureToolbar() {
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            toolbar.title = destination.label

            if (destination.id != controller.graph.startDestinationId) {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener { navController.navigateUp() }
            } else {
                activity.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toolbar.setNavigationOnClickListener(null)
            }
        }
    }
}
