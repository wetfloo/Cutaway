package io.wetfloo.cutaway.ui.core.model

import androidx.annotation.StringRes

data class DrawerMenuItem(
    val destination: DrawerDestination,
    @StringRes val text: Int,
)
