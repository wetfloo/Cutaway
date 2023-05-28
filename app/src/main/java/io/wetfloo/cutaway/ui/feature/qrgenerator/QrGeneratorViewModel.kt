package io.wetfloo.cutaway.ui.feature.qrgenerator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import io.wetfloo.cutaway.core.common.RICKROLL_URL
import io.wetfloo.cutaway.misc.Feedbacker
import io.wetfloo.cutaway.misc.QrRenderer
import io.wetfloo.cutaway.ui.feature.qrgenerator.state.QrGeneratorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrGeneratorViewModel @Inject constructor(
    private val qrRenderer: QrRenderer,
    private val feedbacker: Feedbacker,
) : ViewModel() {
    private val _qrGeneratorState: MutableStateFlow<QrGeneratorState> = MutableStateFlow(
        value = QrGeneratorState(),
    )
    val qrGeneratorState = _qrGeneratorState.asStateFlow()

    fun generateQr(content: String = RICKROLL_URL) {
        viewModelScope.launch {
            updateState {
                it.copy(isLoading = true)
            }

            qrRenderer.renderQr(
                content = content,
            ).onSuccess { bitmap ->
                updateState {
                    it.copy(qrBitmap = bitmap)
                }
            }

            updateState {
                it.copy(isLoading = false)
            }

            feedbacker.feedback()
        }
    }

    private inline fun updateState(block: (QrGeneratorState) -> QrGeneratorState) {
        _qrGeneratorState.update { state ->
            block(state)
        }
    }
}
