package io.wetfloo.cutaway.ui.feature.qrgenerator

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.qrgenerator.state.QrGeneratorState

@AndroidEntryPoint
class QrGeneratorFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: QrGeneratorViewModel by viewModels()
    private val args: QrGeneratorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.generateQr(profileId = args.profileId)

        binding.composeView.composify {
            val state by viewModel
                .qrGeneratorState
                .collectAsStateWithLifecycle(
                    initialValue = QrGeneratorState(),
                    lifecycle = viewLifecycleOwner.lifecycle,
                )

            QrGeneratorScreen(
                state = state,
                navController = { findNavController() },
            )
        }
    }
}
