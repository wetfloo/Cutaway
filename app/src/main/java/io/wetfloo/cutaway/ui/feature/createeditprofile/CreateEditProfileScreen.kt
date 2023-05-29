package io.wetfloo.cutaway.ui.feature.createeditprofile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.wetfloo.cutaway.core.commonimpl.UiError
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileScreenMessage
import io.wetfloo.cutaway.ui.feature.createeditprofile.state.CreateEditProfileState
import kotlinx.coroutines.flow.Flow

@Composable
fun CreateEditProfileScreen(
    navController: () -> NavController,
    onMessage: (CreateEditProfileScreenMessage) -> Unit,
    state: CreateEditProfileState,
    errorFlow: Flow<UiError>,
) {

}
