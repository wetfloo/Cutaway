package io.wetfloo.cutaway.ui.feature.auth

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : ComposeFragment() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            var loginValue by remember {
                mutableStateOf("")
            }

            var passwordValue by remember {
                mutableStateOf("")
            }

            var isLoading by remember {
                mutableStateOf(false)
            }

            val coroutineScope = rememberCoroutineScope()

            AuthScreen(
                loginValue = loginValue,
                passwordValue = passwordValue,
                onLoginChange = { loginValue = it },
                onPasswordChange = { passwordValue = it },
                isLoading = isLoading,
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        delay(5000)
                        isLoading = false
                    }
                },
            )
        }
    }
}
