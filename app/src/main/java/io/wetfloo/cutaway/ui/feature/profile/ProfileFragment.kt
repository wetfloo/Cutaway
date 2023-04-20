package io.wetfloo.cutaway.ui.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileScreenMessage

@AndroidEntryPoint
class ProfileFragment : ComposeFragment() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            val state by viewModel
                .state
                .collectAsStateWithLifecycle()

            val navController = remember {
                findNavController()
            }

            ProfileScreen(
                state = state,
                navController = navController,
                onMessage = { message ->
                    when (message) {
                        ProfileScreenMessage.EditProfile -> viewModel.showEditingNotSupported()
                        ProfileScreenMessage.ShowQrCode -> navController.navigate(
                            directions = ProfileFragmentDirections
                                .actionProfileFragmentToQrGeneratorFragment(),
                        )
                    }
                },
                eventFlow = viewModel.event,
            )
        }
    }
}
