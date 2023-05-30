package io.wetfloo.cutaway.ui.feature.createeditprofile

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.setNavigationResult
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileEvent
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileScreenMessage
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CreateEditProfileFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: CreateEditProfileViewModel by viewModels()
    private val args: CreateEditProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initProfile(args.mode)

        binding.composeView.composify {
            val state by viewModel
                .stateFlow
                .collectAsStateWithLifecycle()

            CreateEditProfileScreen(
                state = state,
                errorFlow = viewModel.error,
                title = stringResource(args.mode.nameRes),

                onMessage = { message ->
                    when (message) {
                        CreateEditProfileScreenMessage.GoBack -> findNavController().popBackStack()
                        CreateEditProfileScreenMessage.Save -> viewModel.commitUpdate(args.mode)
                        is CreateEditProfileScreenMessage.UpdateProfile -> viewModel.updateProfile(message.profileInformation)
                    }
                }
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { createEditEvent ->
                    when (createEditEvent) {
                        CreateEditProfileEvent.Saved -> {
                            val stateValue = viewModel.stateFlow.value
                            setNavigationResult(
                                key = Keys.UPDATED,
                                value = stateValue,
                            )

                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    object Keys {
        const val UPDATED = "UPDATED"
    }
}
