package io.wetfloo.cutaway.ui.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.github.michaelbull.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class AuthViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var loginValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    var passwordValue by savedStateHandle.saveable {
        mutableStateOf("")
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _authResult: MutableEventFlow<Result<*, *>> = MutableEventFlow()
    val authResult = _authResult.asEventFlow()

    fun logIn() {
        viewModelScope.launch {
            _isLoading.emit(true)
            delay(5000)
            _isLoading.emit(false)
        }
    }
}
