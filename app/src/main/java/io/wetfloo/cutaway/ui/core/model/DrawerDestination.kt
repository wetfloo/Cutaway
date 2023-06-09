package io.wetfloo.cutaway.ui.core.model

import androidx.annotation.IdRes
import androidx.annotation.StringRes

data class DrawerDestination(
    @StringRes val textId: Int,
    @IdRes val id: Int,
)
