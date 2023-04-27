package io.wetfloo.cutaway.ui.feature.qr

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.michaelbull.result.Ok
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.qr.state.QrEvent
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QrFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: QrViewModel by viewModels()

    private val barcodeLauncher = registerForActivityResult(
        ScanContract(),
        ::scanResult,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scanOptions = ScanOptions()
            .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            .setBarcodeImageEnabled(true)
            .setOrientationLocked(false)
            .setBeepEnabled(false)
        barcodeLauncher.launch(scanOptions)

        binding.composeView.composify {
            QrScreen(
                navController = { findNavController() },
                eventFlow = viewModel.event,
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.consumeAndNotify { eventResult ->
                    if (eventResult !is Ok) return@consumeAndNotify false

                    when (val qrEvent = eventResult.value) {
                        is QrEvent.UrlScanned -> {
                            Toast.makeText(
                                requireContext(),
                                qrEvent.url,
                                Toast.LENGTH_SHORT,
                            ).show()
                            true
                        }
                    }
                }
            }
        }
    }

    private fun scanResult(result: ScanIntentResult?) {
        viewModel.scanResult(result)
    }
}
