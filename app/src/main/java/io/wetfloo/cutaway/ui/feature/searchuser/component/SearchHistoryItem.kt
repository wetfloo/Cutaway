package io.wetfloo.cutaway.ui.feature.searchuser.component

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.wetfloo.cutaway.data.model.searchuser.SearchHistoryItem

@Composable
fun SearchHistoryItem(
    history: SearchHistoryItem,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(text = history.query)
    }
}
