package io.wetfloo.cutaway.ui.feature.auth

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment

@AndroidEntryPoint
class AuthFragment : ComposeFragment() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            val isLoading by viewModel
                .isLoading
                .collectAsStateWithLifecycle()

            AuthScreen(
                loginValue = viewModel.loginValue,
                passwordValue = viewModel.passwordValue,
                onLoginChange = { viewModel.loginValue = it },
                onPasswordChange = { viewModel.passwordValue = it },
                isLoading = isLoading,
                onClick = viewModel::logIn,
            )
        }
    }
}
