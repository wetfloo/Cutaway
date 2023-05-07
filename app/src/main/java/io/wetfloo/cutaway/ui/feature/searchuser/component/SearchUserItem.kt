package io.wetfloo.cutaway.ui.feature.searchuser.component

import androidx.compose.foundation.layout.Box
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
import io.wetfloo.cutaway.data.model.searchuser.FoundUser

@Composable
fun SearchUserItem(
    user: FoundUser,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.default_padding)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = user.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
