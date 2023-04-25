package io.wetfloo.cutaway.ui.feature.profiledetails

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import io.wetfloo.cutaway.ui.component.DefaultDivider
import io.wetfloo.cutaway.ui.feature.profile.component.ProfileInformationItem
import io.wetfloo.cutaway.ui.feature.profiledetails.state.ProfileDetailedScreenMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailedInformationScreen(
    data: ProfileInformation,
    onMessage: (ProfileDetailedScreenMessage) -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(data.name)
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onMessage(ProfileDetailedScreenMessage.GoBack)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null, // TODO
                        )
                    }
                }
            )
        },
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPaddingValues),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                itemsIndexed(
                    items = data.pieces,
                    key = { _, item ->
                        item.hashCode()
                    }
                ) { index, item ->
                    ProfileInformationItem(
                        modifier = Modifier
                            .fillMaxWidth(),
                        piece = item,
                        onClick = {
                            Toast.makeText(
                                context,
                                "Profile info piece clicked", // TODO
                                Toast.LENGTH_SHORT,
                            ).show()
                        },
                    )

                    if (index != data.pieces.lastIndex) {
                        DefaultDivider(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .sizeIn(maxWidth = 120.dp),
                        )
                    }
                }
            }
        }
    }
}