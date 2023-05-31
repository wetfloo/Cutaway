package io.wetfloo.cutaway.ui.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.LogoutStateEventBus
import io.wetfloo.cutaway.data.preferences.AuthPreferencesStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var preferencesManager: AuthPreferencesStorage

    @Inject
    lateinit var holder: ActivityStartDestinationSelectionHolder

    @Inject
    lateinit var logoutStateEventBus: LogoutStateEventBus

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
        observeLogoutStatus()
    }

    /**
     * Decides if login or home screen should be navigated to
     *
     * Don't forget to use [setContentView] before you call this method,
     * as it relies on [NavHostFragment] being available
     */
    private fun setStartingDestination() {
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

    private fun observeLogoutStatus() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                logoutStateEventBus.events.collect {
                    // don't navigate if we're already there
                    if (navController.currentDestination?.id == R.id.authFragment) {
                        return@collect
                    }

                    val backStack = navController.backQueue
                    val firstDestinationId = backStack
                        .firstOrNull()
                        ?.destination
                        ?.id
                        ?: return@collect

                    navController.navigate(
                        resId = R.id.authFragment,
                        args = null,
                        navOptions = navOptions {
                            // idk why it doesn't pop until current fragment, so this is what needs to be done
                            popUpTo(firstDestinationId) {
                                inclusive = false
                            }
                        },
                    )

                    // clear out all ViewModels scoped to current activity
                    viewModelStore.clear()
                }
            }
        }
    }

    private val hostFragment
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    private val navController
        get() = hostFragment.navController
}
