package io.wetfloo.cutaway.ui.feature.profile

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import io.wetfloo.cutaway.core.commonimpl.EventResultFlow
import io.wetfloo.cutaway.core.ui.compose.core.AppTheme
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.misc.utils.demo
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationBlock
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationTop
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileEvent
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState

@Composable
fun ProfileScreen(
    navController: () -> NavController,
    onMessage: (ProfileScreenMessage) -> Unit,
    state: ProfileState,
    eventFlow: EventResultFlow<ProfileEvent>,
) {
    EventFlowSnackbarDisplay(eventFlow = eventFlow) { snackbarHostState ->
        HostScaffold(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            title = stringResource(R.string.profile_destination_name),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            actions = {
                IconButton(
                    onClick = {
                        onMessage(ProfileScreenMessage.EditProfile)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.profile_edit),
                    )
                }

                IconButton(
                    onClick = {
                        onMessage(ProfileScreenMessage.ShowQrCode)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = null, // TODO
                    )
                }
            },
        ) { scaffoldPaddingValues ->
            Box(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize(),
            ) {
                when (state) {
                    is ProfileState.Ready -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(dimensionResource(R.dimen.default_padding)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ProfileInformationTop(
                            data = state.data,
                            onCardClick = {
                                onMessage(ProfileScreenMessage.ShowProfileDetailedInformation)
                            },
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

                    ProfileState.Idle -> Unit

                    ProfileState.Loading -> CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }
            }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    val context = LocalContext.current

    AppTheme {
        ProfileScreen(
            state = ProfileState.Ready(
                data = ProfileInformation.demo,
            ),
            navController = { NavController(context) },
            onMessage = {},
            eventFlow = MutableEventFlow(),
        )
    }
}
