package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.mapEither
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.model.auth.AuthRequest
import io.wetfloo.cutaway.data.repository.auth.AuthRepository
import io.wetfloo.cutaway.ui.feature.auth.state.AuthEvent
import io.wetfloo.cutaway.ui.feature.auth.state.AuthState
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : StateViewModel<AuthState, AuthEvent, UiError>(
    savedStateHandle = savedStateHandle,
    savedStateKey = STATE,
    defaultStateValue = AuthState.Idle,
) {
    var loginValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    var passwordValue by savedStateHandle.saveable {
        mutableStateOf("")
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
                ).also(mutableEvent::addEvent)

            stateValue = AuthState.Idle
        }
    }

    companion object {
        private const val STATE = "AUTH_STATE"
    }
}
