package io.wetfloo.cutaway.ui.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment
import io.wetfloo.cutaway.core.common.RICKROLL_GIF_URL

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
                imageUrl = RICKROLL_GIF_URL,
                navController = navController,
                onEvent = { event ->
                    when (event) {
                        ProfileScreenEvent.EditProfile -> TODO()
                        ProfileScreenEvent.ShowQrCode -> navController.navigate(
                            directions = ProfileFragmentDirections
                                .actionProfileFragmentToQrGeneratorFragment(),
                        )
                    }
                },
            )
        }
    }
}
