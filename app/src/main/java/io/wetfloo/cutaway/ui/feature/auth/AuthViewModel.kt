package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.booleanWithChance
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import io.wetfloo.cutaway.core.commonimpl.EventResult
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.AuthPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authPreferencesManager: AuthPreferencesManager,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var loginValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    var passwordValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    val authState = savedStateHandle.getStateFlow(
        key = AUTH_STATE,
        initialValue = AuthState(),
    )

    private val _authEvent: MutableEventFlow<EventResult<AuthEvent>> = MutableEventFlow()
    val authEvent = _authEvent.asEventFlow()

    fun logIn() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                )
            }

            delay(3000)

            with(_authEvent) {
                if (booleanWithChance(0.9)) {
                    // it's important to set token before adding any events,
                    // but this logic should be in a repository anyway
                    authPreferencesManager.setToken("sometoken")
                    addEvent(Ok(AuthEvent.Success))
                } else {
                    addEvent(Err(UiError.Resource(R.string.auth_random_error)))
                }
            }

            updateState {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }

    private inline fun updateState(block: (AuthState) -> AuthState) {
        val oldValue: AuthState = savedStateHandle[AUTH_STATE] ?: AuthState()
        val newValue = block(oldValue)
        savedStateHandle[AUTH_STATE] = newValue
    }

    companion object {
        private const val AUTH_STATE = "AUTH_STATE"
    }
}
