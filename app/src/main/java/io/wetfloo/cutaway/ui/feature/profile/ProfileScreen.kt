package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
        actions = {
            IconButton(
                onClick = {
                    onEvent(ProfileScreenEvent.EditProfile)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.profile_edit),
                )
            }

            IconButton(
                onClick = {
                    onEvent(ProfileScreenEvent.ShowQrCode)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = null, // TODO
                )
            }
        },
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
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview1() {
    val context = LocalContext.current
    val navController = remember {
        NavController(context)
    }

    ProfileScreen(
        imageUrl = "",
        navController = navController,
        modifier = Modifier
            .fillMaxSize(),
        onEvent = {},
    )
}

sealed interface ProfileScreenEvent {
    object EditProfile : ProfileScreenEvent
    object ShowQrCode : ProfileScreenEvent
}
