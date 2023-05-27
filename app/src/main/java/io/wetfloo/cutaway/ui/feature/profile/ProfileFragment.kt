package io.wetfloo.cutaway.ui.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.load(profileId = args.id)

        binding.composeView.composify {
            val state by viewModel
                .stateFlow
                .collectAsStateWithLifecycle()

            ProfileScreen(
                state = state,
                navController = { findNavController() },
                errorFlow = viewModel.error,

                onMessage = { message ->
                    when (message) {
                        ProfileScreenMessage.EditProfile -> viewModel.showEditingNotSupported()
                        ProfileScreenMessage.ShowProfileDetailedInformation -> {
                            val data = profileData()
                            if (data != null) {
                                findNavController().navigate(
                                    directions = ProfileFragmentDirections
                                        .actionProfileFragmentToProfileDetailedInformationFragment(
                                            profileInformation = data,
                                        )
                                )
                            }
                        }

                        ProfileScreenMessage.ShowQrCode -> {
                            val data = profileData()
                            if (data != null) {
                                findNavController().navigate(
                                    directions = ProfileFragmentDirections
                                        .actionProfileFragmentToQrGeneratorFragment(url = data.url),
                                )
                            }
                        }
                    }
                },
            )
        }
    }

    private fun profileData(): ProfileInformation? {
        val profileState = viewModel.stateFlow.value
        return (profileState as? ProfileState.Ready)?.data
    }
}
