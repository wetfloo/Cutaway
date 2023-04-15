package io.wetfloo.cutaway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        navController.graph = navGraph
    }


}
