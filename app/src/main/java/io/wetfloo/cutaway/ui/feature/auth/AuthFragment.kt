package io.wetfloo.cutaway.ui.feature.auth

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
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.auth.state.AuthEvent
import io.wetfloo.cutaway.ui.feature.auth.state.AuthScreenMessage
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.composify {
            val state by viewModel
                .state
                .collectAsStateWithLifecycle()

            AuthScreen(
                loginValue = viewModel.loginValue,
                passwordValue = viewModel.passwordValue,
                onLoginChange = { viewModel.loginValue = it },
                onPasswordChange = { viewModel.passwordValue = it },
                state = state,
                onMessage = { message ->
                    when (message) {
                        AuthScreenMessage.LoginButtonClicked -> viewModel.logIn()
                    }
                },
                eventFlow = viewModel.event,
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.consumeAndNotify { eventResult ->
                    eventResult.onSuccess { authEvent ->
                        when (authEvent) {
                            AuthEvent.Success -> {
                                findNavController().navigate(
                                    directions = AuthFragmentDirections
                                        .actionAuthFragmentToProfileFragment(),
                                )
                            }
                        }
                    }

                    eventResult is Ok
                }
            }
        }
    }
}
