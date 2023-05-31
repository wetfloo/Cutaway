package io.wetfloo.cutaway.ui.feature.profile

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.HostScaffold
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.component.contentWindowPaddings
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationTop
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: () -> NavController,
    onMessage: (ProfileScreenMessage) -> Unit,
    state: ProfileState,
    errorFlow: Flow<UiError>,
) {
    val coroutineScope = rememberCoroutineScope()

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
                IconButton(
                    onClick = { onMessage(ProfileScreenMessage.CreateProfile) },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            }
        ) { scaffoldPaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val errorMessage = stringResource(R.string.profile_piece_interaction_failure_activity_open)
                val copiedMessage = stringResource(R.string.profile_piece_interaction_copied)

                when (state) {
                    is ProfileState.Ready -> {
                        if (state.data.isNotEmpty()) {
                            Profiles(
                                scaffoldPaddingValues = scaffoldPaddingValues,
                                state = state,
                                onMessage = onMessage,
                                onActivityLaunchFailed = {
                                    coroutineScope.launch {
                                        snackbarHostState.showSnackbar(message = errorMessage)
                                    }
                                },
                                onInfoCopied = {
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(message = copiedMessage)
                                        }
                                    }
                                },
                            )
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(.5f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Text(
                                    text = stringResource(R.string.profile_no_profiles_yet),
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                    }

                    ProfileState.Idle -> Unit

                    ProfileState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(scaffoldPaddingValues),
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(scaffoldPaddingValues)
                                    .align(Alignment.Center),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Profiles(
    scaffoldPaddingValues: PaddingValues,
    onMessage: (ProfileScreenMessage) -> Unit,
    onInfoCopied: () -> Unit,
    onActivityLaunchFailed: () -> Unit,
    state: ProfileState.Ready,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = contentWindowPaddings(scaffoldPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(
            items = state.data,
            key = { index, item ->
                // kinda hate this
                item.id ?: index
            },
        ) { index, item ->
            ProfileInformationTop(
                data = item,
                onCardClick = {
                    onMessage(ProfileScreenMessage.ShowProfileDetailedInformation(item))
                },
                onQrClick = {
                    onMessage(ProfileScreenMessage.ShowQrCode(item))
                },
                onEditClick = {
                    onMessage(ProfileScreenMessage.EditProfile(item))
                },
                modifier = Modifier
                    .fillMaxWidth(),
                onActivityLaunchFailed = onActivityLaunchFailed,
                onInfoCopied = onInfoCopied,
            )

            if (index != state.data.lastIndex) {
                SpacerSized(h = 16.dp)
            }
        }
    }
}
