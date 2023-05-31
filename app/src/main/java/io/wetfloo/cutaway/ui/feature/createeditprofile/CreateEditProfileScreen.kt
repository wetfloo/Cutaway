package io.wetfloo.cutaway.ui.feature.createeditprofile

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
                        .padding(scaffoldPaddingValues)
                        .padding(horizontal = dimensionResource(R.dimen.default_padding)),
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

@OptIn(ExperimentalMaterial3Api::class)
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
            },
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

                is ProfileInformationPiece.Link -> {
                    var isDialogOpen by rememberSaveable {
                        mutableStateOf(false)
                    }

                    if (isDialogOpen) {
                        AlertDialog(
                            onDismissRequest = {
                                isDialogOpen = false
                                if (piece.title.isBlank()) {
                                    onUpdate(
                                        profileInformation.copy(
                                            pieces = profileInformation.pieces - piece,
                                        )
                                    )
                                }
                            },
                        ) {
                            Surface(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                shape = MaterialTheme.shapes.large,
                                tonalElevation = AlertDialogDefaults.TonalElevation,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(dimensionResource(R.dimen.default_padding)),
                                ) {
                                    ProfileEditorItem(
                                        text = stringResource(R.string.create_edit_profile_title_text_field_label),
                                        value = piece.title,
                                        onValueChange = { value ->
                                            onUpdate(
                                                profileInformation.copy(
                                                    pieces = buildList {
                                                        addAll(profileInformation.pieces)
                                                        removeAt(index)
                                                        add(
                                                            index,
                                                            piece.copy(
                                                                title = value,
                                                            ),
                                                        )
                                                    }
                                                )
                                            )
                                        },
                                    )

                                    SpacerSized(h = 24.dp)

                                    TextButton(
                                        onClick = {
                                            isDialogOpen = false
                                            if (piece.title.isBlank()) {
                                                onUpdate(
                                                    profileInformation.copy(
                                                        pieces = profileInformation.pieces - piece,
                                                    )
                                                )
                                            }
                                        },
                                    ) {
                                        Text(text = stringResource(android.R.string.ok))
                                    }
                                }
                            }
                        }
                    }

                    ProfileEditorItem(
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
                        onTitleClick = {
                            isDialogOpen = true
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
        }

        SpacerSized(h = 16.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            val defaultLinkTitle = stringResource(R.string.create_edit_profile_link_title)
            OutlinedButton(
                onClick = {
                    onUpdate(
                        profileInformation.copy(
                            pieces = profileInformation.pieces + ProfileInformationPiece.Link(
                                title = defaultLinkTitle,
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
    onValueChange: (String) -> Unit,
    onTitleClick: ((String) -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    value: String,
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
                .padding(start = 8.dp)
                .clickable { onTitleClick?.invoke(text) },
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
