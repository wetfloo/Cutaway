package io.wetfloo.cutaway.ui.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.preferences.AuthPreferencesManager
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
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.graph)

        if (!holder.isStartingDestinationSelected) {
            val authPreferences = runBlocking {
                preferencesManager.preferencesFlow.first()
            }
            if (authPreferences.token != null) {
                navGraph.setStartDestination(R.id.profileFragment)
            } else {
                navGraph.setStartDestination(R.id.authFragment)
            }
        }
        navController.graph = navGraph
        holder.isStartingDestinationSelected = true
    }

    private val navHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
}
