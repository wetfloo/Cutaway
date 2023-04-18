package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileImage

@Composable
fun ProfileScreen(
    imageUrl: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    onEvent: (ProfileScreenEvent) -> Unit,
) {
    HostScaffold(
        modifier = modifier
            .fillMaxSize(),
        navController = navController,
        title = stringResource(R.string.profile_destination_name),
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues)
                .padding(
                    horizontal = dimensionResource(R.dimen.default_padding_horizontal),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileImage(
                imageData = imageUrl,
            )

            Button(
                onClick = {
                    onEvent(ProfileScreenEvent.EditProfile)
                },
            ) {
                Text(
                    text = stringResource(R.string.profile_edit),
                )
            }
        }
    }
}

sealed interface ProfileScreenEvent {
    object EditProfile : ProfileScreenEvent
}
