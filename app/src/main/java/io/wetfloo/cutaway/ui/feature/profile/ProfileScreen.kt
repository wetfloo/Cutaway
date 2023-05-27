package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationTop
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState
import kotlinx.coroutines.flow.Flow

@Composable
fun ProfileScreen(
    navController: () -> NavController,
    onMessage: (ProfileScreenMessage) -> Unit,
    state: ProfileState,
    errorFlow: Flow<UiError>,
) {
    EventFlowSnackbarDisplay(errorFlow = errorFlow) { snackbarHostState ->
        HostScaffold(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            title = stringResource(R.string.profile_destination_name),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            actions = {
/*                IconButton(
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
                        contentDescription = stringResource(R.string.profile_qr_description),
                    )
                }*/
            },
        ) { scaffoldPaddingValues ->
            Box(
                modifier = Modifier
                    .padding(scaffoldPaddingValues)
                    .fillMaxSize(),
            ) {
                when (state) {
                    is ProfileState.Ready -> LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(dimensionResource(R.dimen.default_padding)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        itemsIndexed(
                            items = state.data,
                            key = { _, item ->
                                item.id
                            },
                        ) { index, item ->
                            ProfileInformationTop(
                                data = item,
                                onCardClick = {
                                    onMessage(ProfileScreenMessage.ShowProfileDetailedInformation(item))
                                },
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )

                            if (index != state.data.lastIndex) {
                                SpacerSized(h = 16.dp)
                            }
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
