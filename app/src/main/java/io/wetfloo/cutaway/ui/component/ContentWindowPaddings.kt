package io.wetfloo.cutaway.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import io.wetfloo.cutaway.R

@Composable
fun contentWindowPaddings(
    scaffoldPadding: PaddingValues,
    appliedPadding: Dp = dimensionResource(R.dimen.default_padding),
): PaddingValues = WindowInsets(
    // without these scaffold paddings LazyColumn will ignore the app bar
    left = appliedPadding + scaffoldPadding.calculateLeftPadding(LocalLayoutDirection.current),
    top = appliedPadding + scaffoldPadding.calculateTopPadding(),
    right = appliedPadding + scaffoldPadding.calculateRightPadding(LocalLayoutDirection.current),
    bottom = appliedPadding,
)
    // we ignore scaffold paddings here and add navigation bar padding ourselves
    .add(WindowInsets.navigationBars)
    .asPaddingValues()
