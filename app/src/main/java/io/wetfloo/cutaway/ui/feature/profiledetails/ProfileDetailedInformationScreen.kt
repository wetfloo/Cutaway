package io.wetfloo.cutaway.ui.feature.profiledetails

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.profile.ProfileInformationPiece
import io.wetfloo.cutaway.ui.component.DefaultDivider
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationItem
import io.wetfloo.cutaway.ui.feature.profiledetails.state.ProfileDetailedScreenMessage
import io.wetfloo.cutaway.ui.feature.profiledetails.state.ProfileDetailedState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailedInformationScreen(
    state: ProfileDetailedState,
    onMessage: (ProfileDetailedScreenMessage) -> Unit,
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.profile_detail_destination_name))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onMessage(ProfileDetailedScreenMessage.GoBack)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.navigation_close),
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            when (state) {
                                is ProfileDetailedState.Ready -> {
                                    onMessage(ProfileDetailedScreenMessage.ShowQrCode(state.profileInformation))
                                }

                                ProfileDetailedState.Idle, ProfileDetailedState.Loading -> Unit
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues)
                .padding(
                    horizontal = dimensionResource(R.dimen.default_padding),
                    vertical = dimensionResource(R.dimen.default_padding),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            when (state) {
                ProfileDetailedState.Idle -> Unit
                ProfileDetailedState.Loading -> Box(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                    )
                }

                is ProfileDetailedState.Ready -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .crossfade(true)
                            .data(state.profileInformation.pictureUrl)
                            .fallback(R.drawable.no_image_available)
                            .build(),
                        contentDescription = stringResource(R.string.profile_image_description),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .sizeIn(maxWidth = dimensionResource(R.dimen.max_bar_width))
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(percent = 100)),
                    )

                    if (state.profileInformation.pieces.isNotEmpty()) {
                        DefaultDivider(
                            modifier = Modifier
                                .padding(vertical = 24.dp),
                        )
                    }

                    val errorMessage = stringResource(R.string.profile_piece_interaction_failure_activity_open)
                    val copiedMessage = stringResource(R.string.profile_piece_interaction_copied)

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        itemsIndexed(
                            items = state.profileInformation.pieces,
                            key = { _, item ->
                                item.hashCode()
                            }
                        ) { index, piece ->
                            ProfileInformationItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                piece = piece,
                                onClick = {
                                    when (piece) {
                                        is ProfileInformationPiece.Formed -> {
                                            clipboardManager.setText(AnnotatedString(piece.value))
                                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                                                snackbarHostState.showMessage(
                                                    message = copiedMessage,
                                                    coroutineScope = coroutineScope,
                                                )
                                            }
                                        }

                                        is ProfileInformationPiece.Link -> {
                                            try {
                                                val intent = Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse(piece.url),
                                                )
                                                context.startActivity(intent)
                                            } catch (e: ActivityNotFoundException) {
                                                snackbarHostState.showMessage(
                                                    message = errorMessage,
                                                    coroutineScope = coroutineScope,
                                                )
                                                snackbarHostState.showMessage(
                                                    message = errorMessage,
                                                    coroutineScope = coroutineScope,
                                                )
                                            }
                                        }
                                    }
                                },
                            )

                            if (index != state.profileInformation.pieces.lastIndex) {
                                DefaultDivider(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .sizeIn(maxWidth = 120.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun SnackbarHostState.showMessage(
    message: String,
    coroutineScope: CoroutineScope,
) {
    coroutineScope.launch {
        showSnackbar(message = message)
    }
}
