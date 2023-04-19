package io.wetfloo.cutaway.ui.feature.profile.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.DefaultDivider

@Composable
fun ProfileInformationBlock(
    headline: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.card_spacing_internal)),
        ) {
            Text(
                text = headline,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            DefaultDivider(
                modifier = Modifier
                    .padding(
                        vertical = dimensionResource(R.dimen.divider_spacing),
                    ),
            )

            content()
        }
    }
}
