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
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QrFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: QrViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.scan()

        binding.composeView.composify {
            QrScreen(
                navController = { findNavController() },
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.consumeAndNotify(
                    filter = { it is Ok }
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Scanned a code",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
}
