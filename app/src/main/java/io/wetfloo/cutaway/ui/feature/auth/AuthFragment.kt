package io.wetfloo.cutaway.ui.feature.auth

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment
import io.wetfloo.cutaway.ui.feature.auth.state.AuthEvent
import io.wetfloo.cutaway.ui.feature.auth.state.AuthScreenMessage
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : ComposeFragment() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            val state by viewModel
                .state
                .collectAsStateWithLifecycle()

            AuthScreen(
                loginValue = viewModel.loginValue,
                passwordValue = viewModel.passwordValue,
                onLoginChange = { viewModel.loginValue = it },
                onPasswordChange = { viewModel.passwordValue = it },
                authState = state,
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
                viewModel.event.consumeAndNotify(
                    filter = { it is Ok },
                ) { eventResult ->
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
                }
            }
        }
    }
}
