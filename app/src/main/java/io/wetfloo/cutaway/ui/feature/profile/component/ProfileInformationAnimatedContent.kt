package io.wetfloo.cutaway.ui.feature.profile.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfileInformationAnimatedContent(
    state: ProfileState.Data,
    isDetailedInformationOpen: Boolean,
    onCardClick: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        targetState = isDetailedInformationOpen,
        modifier = modifier,
    ) { isExpanded ->
        if (isExpanded) {
            ProfileInformationDetails(
                state = state,
                onClose = onClose,
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(R.dimen.default_padding)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ProfileInformationTop(
                    state = state,
                    onCardClick = onCardClick,
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
        }
    }
}
