package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileImage

@Composable
fun ProfileScreen(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(R.dimen.default_padding_horizontal),
            ),
    ) {
        ProfileImage(
            imageData = imageUrl,
        )
    }
}
