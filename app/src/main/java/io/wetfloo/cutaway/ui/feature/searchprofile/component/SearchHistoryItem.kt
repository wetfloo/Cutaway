package io.wetfloo.cutaway.ui.feature.searchprofile.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.searchprofile.SearchHistoryItem

@Composable
fun SearchHistoryItem(
    history: SearchHistoryItem,
    onDeleteClick: () -> Unit,
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
                text = history.query,
                maxLines = 1,
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.search_profile_delete_history_item_description),
                )
            }
        }
    }
}
