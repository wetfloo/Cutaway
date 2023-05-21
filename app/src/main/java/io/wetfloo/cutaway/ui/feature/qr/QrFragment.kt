package io.wetfloo.cutaway.ui.feature.qr

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.journeyapps.barcodescanner.ScanIntentResult
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify

@AndroidEntryPoint
class QrFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: QrViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.composify {
            QrScreen(
                navController = { findNavController() },
                errorFlow = viewModel.error,
            )
        }
    }

    /**
     * This is needed to prevent calls from activity contract
     * to the [viewModel] before it's ready
     */
    private fun scanResult(result: ScanIntentResult?) {
        viewModel.scanResult(result)
    }
}
