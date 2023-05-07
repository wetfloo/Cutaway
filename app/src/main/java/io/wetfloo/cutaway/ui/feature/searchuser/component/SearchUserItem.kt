package io.wetfloo.cutaway.ui.feature.searchuser.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.wetfloo.cutaway.data.model.searchuser.FoundUser

@Composable
fun SearchUserItem(
    user: FoundUser,
    modifier: Modifier = Modifier,
) {
    Text(
        text = user.name,
        modifier = modifier,
    )
}
