package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.HostTopAppBar
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    imageUrl: String,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HostTopAppBar(
                text = stringResource(R.string.profile_destination_name),
                onMenuClick = onMenuClick,
            )
        }
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = dimensionResource(R.dimen.default_padding_horizontal),
                )
                .padding(scaffoldPaddingValues),
        ) {
            ProfileImage(
                imageData = imageUrl,
            )
        }
    }
}
