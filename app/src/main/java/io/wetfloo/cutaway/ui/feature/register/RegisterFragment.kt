package io.wetfloo.cutaway.ui.feature.register

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.register.state.RegisterEvent
import io.wetfloo.cutaway.ui.feature.register.state.RegisterScreenMessage
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.composify {
            val state by viewModel
                .stateFlow
                .collectAsStateWithLifecycle()

            RegisterScreen(
                emailValue = viewModel.emailValue,
                loginValue = viewModel.loginValue,
                passwordValue = viewModel.passwordValue,
                onEmailChange = { value ->
                    viewModel.emailValue = value
                },
                onLoginChange = { value ->
                    viewModel.loginValue = value
                },
                onPasswordChange = { value ->
                    viewModel.passwordValue = value
                },
                state = state,
                onMessage = { message ->
                    when (message) {
                        RegisterScreenMessage.RegisterButtonClicked -> viewModel.register()

                        RegisterScreenMessage.GoToAuth -> findNavController().popBackStack()
                    }
                },
                errorFlow = viewModel.error,
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { registerEvent ->
                    when (registerEvent) {
                        RegisterEvent.Success -> {
                            findNavController().navigate(
                                directions = RegisterFragmentDirections
                                    .actionRegisterFragmentToProfileFragment(),
                            )
                        }
                    }
                }
            }
        }
    }
}
