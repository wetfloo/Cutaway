package io.wetfloo.cutaway.ui.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {
    ModalNavigationDrawer(
        drawerContent = {

        },
        modifier = modifier,
    ) {
        Scaffold { scaffoldPaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = dimensionResource(R.dimen.default_padding_horizontal),
                    )
                    .padding(scaffoldPaddingValues),
            ) {
                ProfileImage(
                    imageData = imageUrl,
                )
            }
        }
    }
}
