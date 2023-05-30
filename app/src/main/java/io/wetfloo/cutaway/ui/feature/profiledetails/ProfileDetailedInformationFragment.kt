package io.wetfloo.cutaway.ui.feature.profiledetails

import android.os.Bundle
import android.util.Log
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
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.profiledetails.state.ProfileDetailedScreenMessage

@AndroidEntryPoint
class ProfileDetailedInformationFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: ProfileDetailedInformationViewModel by viewModels()
    private val args: ProfileDetailedInformationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        performStateChecksAndLoadData()

        binding.composeView.composify {
            val state by viewModel
                .stateFlow
                .collectAsStateWithLifecycle()

            ProfileDetailedInformationScreen(
                state = state,
                onMessage = { message ->
                    when (message) {
                        ProfileDetailedScreenMessage.GoBack -> findNavController().popBackStack()

                        is ProfileDetailedScreenMessage.ShowQrCode -> {
                            val profileId = message.profile.id
                            require(profileId != null) {
                                "How is id null here? It shouldn't be!"
                            }
                            findNavController().navigate(
                                directions = ProfileDetailedInformationFragmentDirections
                                    .actionProfileDetailedInformationFragmentToQrGeneratorFragment(
                                        profileId = profileId,
                                    ),
                            )
                        }
                    }
                }
            )
        }
    }

    private fun performStateChecksAndLoadData() {
        val profileId = args.profileId
        val profileInformation = args.profileInformation

        with(viewModel) {
            val bothNull = profileId == null && profileInformation == null
            require(!bothNull) {
                "Impossible profileId and profileInformation values, both are null..."
            }

            val eitherNull = profileId == null || profileInformation == null
            if (!eitherNull) {
                Log.w(
                    TAG,
                    """
                    Somehow, both profileId and profileInformation are not null. 
                    The values are $profileId and $profileInformation, respectively
                    """.trimIndent()
                )
            }

            if (profileId != null) {
                loadProfileInformationById(profileId)
                // correct profileId takes the priority
                return
            }
            if (profileInformation != null) {
                initProfile(profileInformation)
            }
        }
    }

    companion object {
        private const val TAG = "ProfileDetailFragment"
    }
}
