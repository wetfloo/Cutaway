package io.wetfloo.cutaway.ui.core

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentBottomNavigationBinding

@AndroidEntryPoint
class BottomNavigationFragment : Fragment(R.layout.fragment_bottom_navigation) {
    private val binding by viewBinding(FragmentBottomNavigationBinding::bind)
    private val bottomNavigationShownOnDestinationIds by lazy(LazyThreadSafetyMode.NONE) {
        setOf<Int>()
    }
    private lateinit var childNavController: NavController
    private var destinationListener: NavController.OnDestinationChangedListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = childFragmentManager
            .findFragmentById(R.id.navHostFragmentHome) as NavHostFragment
        childNavController = navHostFragment.navController

        setUpBottomNavigationViewHiding()

        binding.bottomNavigationViewHome.setupWithNavController(childNavController)
    }

    /**
     * Hide bottom navigation view when navigating to most fragments,
     * showing it when needed
     */
    private fun setUpBottomNavigationViewHiding() {
        val listener = NavController.OnDestinationChangedListener { _, navDestination, _ ->
            val bottomNavigation = binding.bottomNavigationViewHome
            bottomNavigation.isVisible = navDestination.id in bottomNavigationShownOnDestinationIds
        }
        destinationListener = listener
        childNavController.addOnDestinationChangedListener(listener)
    }

    override fun onDestroyView() {
        destinationListener?.let(childNavController::removeOnDestinationChangedListener)
        destinationListener = null
        super.onDestroyView()
    }
}
