package io.wetfloo.cutaway.ui.feature.createeditprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.forEachInBetweenIndexed
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.ui.component.EventFlowSnackbarDisplay
import io.wetfloo.cutaway.ui.component.NiceTextField
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileState
import kotlinx.coroutines.flow.Flow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditProfileScreen(
    onMessage: (CreateEditProfileScreenMessage) -> Unit,
    title: String,
    state: CreateEditProfileState,
    errorFlow: Flow<UiError>,
) {
    EventFlowSnackbarDisplay(errorFlow = errorFlow) { snackbarHostState ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = title)
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onMessage(CreateEditProfileScreenMessage.GoBack)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.navigation_go_back),
                            )
                        }
                    },
                )
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
        ) { scaffoldPaddingValues ->
            when (state) {
                is CreateEditProfileState.Available -> ProfileEditor(
                    profileInformation = state.profileInformation,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.default_padding))
                        .padding(scaffoldPaddingValues),
                    onUpdate = { profileInformation ->
                        onMessage(CreateEditProfileScreenMessage.UpdateProfile(profileInformation))
                    },
                    onCommit = {
                        onMessage(CreateEditProfileScreenMessage.Save)
                    },
                )

                CreateEditProfileState.Idle -> Unit // TODO
            }
        }
    }
}

@Composable
private fun ProfileEditor(
    profileInformation: ProfileInformation,
    onUpdate: (ProfileInformation) -> Unit,
    onCommit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        ProfileEditorItem(
            text = "Name",
            value = profileInformation.name,
            onValueChange = { value ->
                onUpdate(
                    profileInformation.copy(
                        name = value,
                    )
                )
            }
        )

        SpacerSized(h = 16.dp)

        ProfileEditorItem(
            text = "Last name",
            value = profileInformation.lastName ?: "",
            onValueChange = { value ->
                onUpdate(
                    profileInformation.copy(
                        lastName = value.takeUnless {
                            it.isBlank()
                        }
                    )
                )
            }
        )

        SpacerSized(h = 16.dp)

        profileInformation.pieces.forEachInBetweenIndexed(
            inBetweenBlock = { _, _ ->
                SpacerSized(h = 16.dp)
            },
        ) { index, piece ->
            when (piece) {
                is ProfileInformationPiece.Formed -> {
                    ProfileEditorItem(
                        text = stringResource(piece.type.headerStringRes),
                        value = piece.value,
                        onValueChange = { value ->
                            onUpdate(
                                profileInformation.copy(
                                    pieces = buildList {
                                        addAll(profileInformation.pieces)
                                        removeAt(index)
                                        add(
                                            index = index,
                                            element = piece.copy(
                                                value = value,
                                            )
                                        )
                                    }
                                )
                            )
                        }
                    )
                }

                is ProfileInformationPiece.Link -> Unit
            }
        }

        SpacerSized(h = 32.dp)

        Button(
            onClick = onCommit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                ),
        ) {
            Text(
                text = stringResource(R.string.create_edit_profile_commit),
            )
        }
    }
}

@Composable
private fun ProfileEditorItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 8.dp),
        )
        SpacerSized(h = 8.dp)
        NiceTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

private inline fun ProfileInformation.update(updater: (ProfileInformation) -> ProfileInformation) =
    updater(this)
