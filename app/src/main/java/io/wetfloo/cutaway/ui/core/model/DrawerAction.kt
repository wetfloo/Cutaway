package io.wetfloo.cutaway.ui.core.model

import androidx.annotation.StringRes

data class DrawerAction(
    @StringRes val textId: Int,
    val action: () -> Unit,
)
