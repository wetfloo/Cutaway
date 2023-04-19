package io.wetfloo.cutaway.ui.feature.qrgenerator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import io.wetfloo.cutaway.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrGeneratorScreen(
    qrGeneratorState: QrGeneratorState,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.qr_generator_destination_name),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.app_bar_back),
                        )
                    }
                },
            )
        },
    ) { scaffoldPaddingValues ->
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.default_padding))
                .padding(scaffoldPaddingValues),
        ) {
            if (qrGeneratorState.isLoading) {
                CircularProgressIndicator() // TODO this is huge for some reason
            } else {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = qrGeneratorState.qrBitmap,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )
            }

        }
    }
}
