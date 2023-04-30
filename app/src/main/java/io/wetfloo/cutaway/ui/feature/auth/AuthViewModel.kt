package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.booleanWithChance
import io.wetfloo.cutaway.core.commonimpl.StateViewModel
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.AuthPreferencesManager
import io.wetfloo.cutaway.ui.feature.auth.state.AuthEvent
import io.wetfloo.cutaway.ui.feature.auth.state.AuthState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authPreferencesManager: AuthPreferencesManager,
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

            delay(3000)

            with(mutableEvent) {
                if (booleanWithChance(0.9)) {
                    // it's important to set token before adding any events,
                    // but this logic should be in a repository anyway
                    authPreferencesManager.setToken("sometoken")
                    addEvent(Ok(AuthEvent.Success))
                } else {
                    addEvent(Err(UiError.Res(R.string.auth_random_error)))
                }
            }

            stateValue = AuthState.Idle
        }
    }

    companion object {
        private const val STATE = "AUTH_STATE"
    }
}
