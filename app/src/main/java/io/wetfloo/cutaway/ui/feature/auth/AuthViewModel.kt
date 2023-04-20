package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import io.wetfloo.cutaway.data.AuthPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authPreferencesManager: AuthPreferencesManager,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var loginValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    var passwordValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _authResult: MutableEventFlow<Result<*, *>> = MutableEventFlow()
    val authResult = _authResult.asEventFlow()

    fun logIn() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isLoading = true,
                )
            }

            delay(3000)

            authPreferencesManager.setToken("fklefe")
            _authResult.addEvent(Ok(Unit))

            updateState {
                it.copy(
                    isLoading = false,
                )
            }
        }
    }

    private inline fun updateState(block: (AuthState) -> AuthState) {
        _authState.update { state ->
            block(state)
        }
    }
}
