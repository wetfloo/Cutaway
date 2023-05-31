package io.wetfloo.cutaway.ui.feature.createeditprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.forEachInBetweenIndexed
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.data.model.profile.ProfileLinkType
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
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.default_padding))
                .imePadding(),
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
        SpacerSized(h = dimensionResource(R.dimen.default_padding))

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
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(piece.type.headerStringRes),
                        value = piece.value,
                        onDelete = {
                            onUpdate(
                                profileInformation.copy(
                                    pieces = profileInformation.pieces - piece,
                                )
                            )
                        },
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

                is ProfileInformationPiece.Link -> ProfileEditorItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = piece.title,
                    value = piece.url,
                    onDelete = {
                        onUpdate(
                            profileInformation.copy(
                                pieces = profileInformation.pieces - piece,
                            )
                        )
                    },
                    onValueChange = { value ->
                        onUpdate(
                            profileInformation.copy(
                                pieces = buildList {
                                    addAll(profileInformation.pieces)
                                    removeAt(index)
                                    add(
                                        index = index,
                                        element = piece.copy(
                                            url = value,
                                        )
                                    )
                                }
                            )
                        )
                    }
                )
            }
        }

        SpacerSized(h = 16.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            OutlinedButton(
                onClick = {
                    onUpdate(
                        profileInformation.copy(
                            pieces = profileInformation.pieces + ProfileInformationPiece.Link(
                                title = "My custom link",
                                url = "",
                                linkType = ProfileLinkType.CUSTOM,
                            ),
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(.5f),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )

                Text(text = "Add link")
            }
        }

        SpacerSized(h = 16.dp)

        Button(
            onClick = onCommit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.create_edit_profile_commit),
            )
        }

        SpacerSized(h = dimensionResource(R.dimen.default_padding))
    }
}

@Composable
private fun ProfileEditorItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDelete: (() -> Unit)? = null,
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
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NiceTextField(
                value = value,
                onValueChange = onValueChange,
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .weight(1f),
            )
            if (onDelete != null) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}
