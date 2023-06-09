package io.wetfloo.cutaway.ui.feature.searchprofile.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.ui.component.BoxLoadingIndicator
import io.wetfloo.cutaway.ui.component.NiceTextField
import io.wetfloo.cutaway.ui.component.SpacerSized
import io.wetfloo.cutaway.ui.feature.searchprofile.state.SearchProfileState

@Composable
fun SearchProfileContent(
    modifier: Modifier = Modifier,
    requestFocusOnStart: Boolean = true,
    onSearchRequested: () -> Unit,
    onItemClicked: (ProfileInformation) -> Unit,
    onQueryChange: (String) -> Unit,
    queryValue: String,
    state: SearchProfileState,
) {
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LaunchedEffect(Unit) {
            if (requestFocusOnStart) {
                focusRequester.requestFocus()
            }
        }

        SpacerSized(h = 16.dp)

        NiceTextField(
            value = queryValue,
            onValueChange = onQueryChange,
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.default_padding))
                .sizeIn(maxWidth = dimensionResource(R.dimen.max_bar_width)),
            placeholder = {
                Text(text = stringResource(R.string.ui_search))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = queryValue.isNotBlank(),
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    IconButton(
                        onClick = { onQueryChange("") },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchRequested() },
            ),
        )

        SpacerSized(h = 16.dp)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
        ) {
            when (state) {
                is SearchProfileState.Found -> {
                    Column {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                        ) {
                            items(items = state.profiles) { profile ->
                                SearchProfileItem(
                                    profile = profile,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeightIn(min = dimensionResource(R.dimen.list_item_min_height))
                                        .clickable {
                                            onItemClicked(profile)
                                        },
                                )
                            }
                        }
                    }
                }

                SearchProfileState.Idle -> Unit

                SearchProfileState.Loading -> BoxLoadingIndicator()
            }
        }
    }
}
