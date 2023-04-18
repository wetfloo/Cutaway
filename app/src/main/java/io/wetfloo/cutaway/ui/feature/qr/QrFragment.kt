package io.wetfloo.cutaway.ui.feature.qr

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.HostScaffold

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class QrFragment : ComposeFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            val navController = remember {
                findNavController()
            }
            HostScaffold(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize(),
                title = stringResource(R.string.qr_scanner_destination_name),
            ) { scaffoldPaddingValues ->
                QrScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(scaffoldPaddingValues),
                )
            }
        }
    }
}
