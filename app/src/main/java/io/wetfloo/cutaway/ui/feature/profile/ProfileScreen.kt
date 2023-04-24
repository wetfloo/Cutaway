package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow
import io.wetfloo.cutaway.core.commonimpl.EventResultFlow
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationAnimatedContent
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
                var isDetailedInformationOpen by rememberSaveable {
                    mutableStateOf(false)
                }

                when (state) {
                    is ProfileState.Data -> ProfileInformationAnimatedContent(
                        state = state,
                        isDetailedInformationOpen = isDetailedInformationOpen,
                        onCardClick = {
                            isDetailedInformationOpen = true
                        },
                        onClose = {
                            isDetailedInformationOpen = false
                        },
                    )

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

@Preview
@Composable
private fun ProfileScreenPreview1() {
    val context = LocalContext.current

    ProfileScreen(
        state = ProfileState.Data(
            name = "Creative name",
            status = "Ligma male",
            pictureUrl = null,
            pieces = emptyList(),
        ),
        navController = { NavController(context) },
        onMessage = {},
        eventFlow = MutableEventFlow(),
    )
}
