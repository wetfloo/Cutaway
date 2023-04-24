package io.wetfloo.cutaway.ui.feature.profile.component

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.wetfloo.cutaway.ui.component.DefaultDivider
import io.wetfloo.cutaway.ui.feature.profile.state.ProfileState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileInformationDetails(
    state: ProfileState.Data,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    //    Scaffold(
    //        modifier = modifier
    //            .fillMaxSize(),
    //        topBar = {
    //            TopAppBar(
    //                title = {
    //                    Text(state.name)
    //                },
    //                navigationIcon = {
    //                    IconButton(onClick = onClose) {
    //                        Icon(
    //                            imageVector = Icons.Default.Close,
    //                            contentDescription = null, // TODO
    //                        )
    //                    }
    //                }
    //            )
    //        },
    //    ) { scaffoldPaddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
        //                .padding(scaffoldPaddingValues),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            itemsIndexed(
                items = state.pieces,
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

                if (index != state.pieces.lastIndex) {
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
//}
