package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.mapEither
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import io.wetfloo.cutaway.data.repository.auth.AuthRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.auth.state.AuthEvent
import io.wetfloo.cutaway.ui.feature.auth.state.AuthState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<AuthState>(
        savedStateHandle = savedStateHandle,
        key = "AUTH_STATE",
        defaultState = AuthState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    private val _event: Channel<AuthEvent> = Channel()
    val event = _event.receiveAsFlow()

    var loginValue by savedStateHandle.saveable {
        mutableStateOf("someUsername")
    }

    var passwordValue by savedStateHandle.saveable {
        mutableStateOf("123456Aa_!")
    }

    fun logIn() {
        viewModelScope.launch {
            stateValue = AuthState.Loading

            val authRequest = AuthRequest(
                login = loginValue,
                password = passwordValue,
            )
            authRepository
                .authenticate(authRequest)
                .mapEither(
                    success = { AuthEvent.Success },
                    failure = { UiError.Res(R.string.auth_failure_general) },
                )
                .onFailure { _error.send(it) }
                .onSuccess { _event.send(it) }

            stateValue = AuthState.Idle
        }
    }
}
