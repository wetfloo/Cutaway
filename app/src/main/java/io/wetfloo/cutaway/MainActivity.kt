package io.wetfloo.cutaway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
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
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.graph)

        navGraph.setStartDestination(R.id.authFragment)

        navController.graph = navGraph
    }
}
