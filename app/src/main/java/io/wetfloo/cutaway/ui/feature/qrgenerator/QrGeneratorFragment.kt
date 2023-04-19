package io.wetfloo.cutaway.ui.feature.qrgenerator

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment

@AndroidEntryPoint
class QrGeneratorFragment : ComposeFragment() {
    private val viewModel: QrGeneratorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.generateQr()

        drawContent {
            val state by viewModel
                .qrGeneratorState
                .collectAsStateWithLifecycle(
                    initialValue = QrGeneratorState(),
                    lifecycle = viewLifecycleOwner.lifecycle,
                )

            QrGeneratorScreen(
                qrGeneratorState = state,
            )
        }
    }
}
