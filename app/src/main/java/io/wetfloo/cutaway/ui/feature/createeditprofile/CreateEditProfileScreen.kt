package io.wetfloo.cutaway.ui.feature.createeditprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditMode
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileState
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditProfileScreen(
    navController: () -> NavController,
    onMessage: (CreateEditProfileScreenMessage) -> Unit,
    mode: CreateEditMode,
    state: CreateEditProfileState,
    errorFlow: Flow<UiError>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(mode.nameRes))
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
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues)
                .padding(
                    horizontal = dimensionResource(R.dimen.default_padding),
                    vertical = dimensionResource(R.dimen.default_padding),
                ),
        ) {

        }
    }
}
