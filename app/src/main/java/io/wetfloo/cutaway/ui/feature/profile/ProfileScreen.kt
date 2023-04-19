package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationBlock
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationTop

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
                .verticalScroll(rememberScrollState())
                .padding(scaffoldPaddingValues)
                .padding(dimensionResource(R.dimen.default_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileInformationTop(
                imageData = imageUrl,
                modifier = Modifier
                    .fillMaxWidth(),
            )

            SpacerSized(h = dimensionResource(R.dimen.default_card_spacing_vertical_external))

            ProfileInformationBlock(
                headline = "Profile info headline",
            ) {
                Text(
                    text = "This is a sample text to put content inside of ProfileInformationBlock",
                )
            }
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
