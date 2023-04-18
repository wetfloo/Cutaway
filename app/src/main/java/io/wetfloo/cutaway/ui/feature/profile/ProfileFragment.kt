package io.wetfloo.cutaway.ui.feature.profile

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.HostScaffold

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class ProfileFragment : ComposeFragment() {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawContent {
            val navController = remember {
                findNavController()
            }

            HostScaffold(
                navController = navController,
                title = stringResource(R.string.profile_destination_name),
            ) { scaffoldPaddingValues ->
                ProfileScreen(
                    imageUrl = "",
                    modifier = Modifier
                        .padding(scaffoldPaddingValues),
                )
            }
        }
    }
}
