package io.wetfloo.cutaway.ui.core.model

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

sealed interface DrawerDestination {
    @get:StringRes
    val textId: Int

    data class Identifiable(
        @IdRes val id: Int,
        @StringRes override val textId: Int,
    ) : DrawerDestination

    @Immutable
    data class Actionable(
        val action: () -> Unit,
        @StringRes override val textId: Int,
    ) : DrawerDestination
}
