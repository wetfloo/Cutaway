package io.wetfloo.cutaway.ui.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment

@AndroidEntryPoint
class ProfileFragment : ComposeFragment() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            val navController = remember {
                findNavController()
            }

            ProfileScreen(
                imageUrl = RICKROLL_URL,
                navController = navController,
                onEvent = {},
            )
        }
    }

    companion object {
        private const val RICKROLL_URL =
            "https://www.icegif.com/wp-content/uploads/rick-roll-icegif-5.gif"
    }
}
