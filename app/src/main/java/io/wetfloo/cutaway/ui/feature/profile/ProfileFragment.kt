package io.wetfloo.cutaway.ui.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.load()

        binding.composeView.composify {
            val state by viewModel
                .state
                .collectAsStateWithLifecycle()

            ProfileScreen(
                state = state,
                navController = { findNavController() },
                onMessage = { message ->
                    when (message) {
                        ProfileScreenMessage.EditProfile -> viewModel.showEditingNotSupported()
                        ProfileScreenMessage.ShowProfileDetailedInformation -> {
                            val profileState = viewModel.state.value

                            if (profileState is ProfileState.Ready) {
                                findNavController().navigate(
                                    directions = ProfileFragmentDirections
                                        .actionProfileFragmentToProfileDetailedInformationFragment(
                                            profileInformation = profileState.data,
                                        )
                                )
                            }
                        }

                        ProfileScreenMessage.ShowQrCode -> {
                            findNavController().navigate(
                                directions = ProfileFragmentDirections
                                    .actionProfileFragmentToQrGeneratorFragment(),
                            )
                        }
                    }
                },
                eventFlow = viewModel.event,
            )
        }
    }
}
