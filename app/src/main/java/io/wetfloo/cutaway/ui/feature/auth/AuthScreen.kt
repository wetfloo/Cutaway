package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.NiceTextField
import io.wetfloo.cutaway.ui.feature.auth.state.AuthScreenMessage
import io.wetfloo.cutaway.ui.feature.auth.state.AuthState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
)
@Composable
fun AuthScreen(
    loginValue: String,
    passwordValue: String,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: AuthState,
    onMessage: (AuthScreenMessage) -> Unit,
    errorFlow: Flow<UiError>,
) {
    EventFlowSnackbarDisplay(errorFlow = errorFlow) { snackbarHostState ->
        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { scaffoldPaddingValues ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .padding(horizontal = dimensionResource(R.dimen.default_padding))
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.log_in_headline),
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Spacer(Modifier.height(32.dp))

                    NiceTextField(
                        value = loginValue,
                        onValueChange = onLoginChange,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.login),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )

                    Spacer(Modifier.height(16.dp))

                    NiceTextField(
                        value = passwordValue,
                        onValueChange = onPasswordChange,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password),
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = {
                            onMessage(AuthScreenMessage.LoginButtonClicked)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                            ),
                    ) {
                        AnimatedContent(targetState = state == AuthState.Loading) { loading ->
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(12.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp,
                                )

                                Spacer(Modifier.width(20.dp))
                            }
                        }
                        Text(
                            text = stringResource(R.string.log_in),
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun AuthScreenPreview1() {
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
        state = AuthState.Idle,
        onMessage = { message ->
            when (message) {
                AuthScreenMessage.LoginButtonClicked -> {
                    coroutineScope.launch {
                        isLoading = true
                        delay(5000)
                        isLoading = false
                    }
                }
            }
        },
        errorFlow = emptyFlow(),
    )
}
