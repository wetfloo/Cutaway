package io.wetfloo.cutaway.ui.feature.register

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
import io.wetfloo.cutaway.core.commonimpl.Res
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.core.commonimpl.logW
import io.wetfloo.cutaway.data.model.register.RegisterRequest
import io.wetfloo.cutaway.data.repository.auth.AuthRepository
import io.wetfloo.cutaway.misc.utils.savedastate.StateSaver
import io.wetfloo.cutaway.ui.feature.register.state.RegisterEvent
import io.wetfloo.cutaway.ui.feature.register.state.RegisterState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val stateSaver = StateSaver<RegisterState>(
        savedStateHandle = savedStateHandle,
        key = "AUTH_STATE",
        defaultState = RegisterState.Idle,
    )
    private var stateValue by stateSaver
    val stateFlow = stateSaver.state

    private val _error: Channel<UiError> = Channel()
    val error = _error.receiveAsFlow()

    private val _event: Channel<RegisterEvent> = Channel()
    val event = _event.receiveAsFlow()

    var emailValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    var loginValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    var passwordValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    fun register() {
        viewModelScope.launch {
            stateValue = RegisterState.Loading

            val registerRequest = RegisterRequest(
                email = emailValue,
                login = loginValue,
                password = passwordValue,
            )
            authRepository
                .register(registerRequest)
                .logW(TAG)
                .mapEither(
                    success = { RegisterEvent.Success },
                    failure = { Res(R.string.register_failure_generic) },
                )
                .onFailure { _error.send(it) }
                .onSuccess { _event.send(it) }

            stateValue = RegisterState.Idle
        }
    }

    companion object {
        private const val TAG = "RegisterViewModel"
    }
}
