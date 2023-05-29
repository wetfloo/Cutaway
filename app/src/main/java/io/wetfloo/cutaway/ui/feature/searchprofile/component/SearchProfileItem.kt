package io.wetfloo.cutaway.ui.feature.searchprofile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.profile.ProfileInformation

@Composable
fun SearchProfileItem(
    profile: ProfileInformation,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.default_padding)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = profile.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
