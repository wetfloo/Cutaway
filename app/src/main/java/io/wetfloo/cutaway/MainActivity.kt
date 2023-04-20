package io.wetfloo.cutaway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.data.AuthPreferencesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var preferencesManager: AuthPreferencesManager

    @Inject
    lateinit var holder: ActivityStartDestinationSelectionHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(
            /* window = */
            window,
            /* decorFitsSystemWindows = */
            false,
        )
        setContentView(R.layout.activity_main)
        setStartingDestination()
    }

    /**
     * Decides if login or home screen should be navigated to
     *
     * Don't forget to use [setContentView] before you call this method,
     * as it relies on [NavHostFragment] being available
     */
    private fun setStartingDestination() {
        if (holder.isStartingDestinationSelected) return

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.graph)

        val authPreferences = runBlocking {
            preferencesManager.preferencesFlow.first()
        }

        if (authPreferences.token != null) {
            navGraph.setStartDestination(R.id.profileFragment)
        } else {
            navGraph.setStartDestination(R.id.authFragment)
        }
        navController.graph = navGraph

        holder.isStartingDestinationSelected = true
    }
}
