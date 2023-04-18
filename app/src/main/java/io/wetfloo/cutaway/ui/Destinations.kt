package io.wetfloo.cutaway.ui

import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.DrawerMenuItem

val destinations: List<DrawerMenuItem> by lazy(LazyThreadSafetyMode.PUBLICATION) {
    listOf(
        DrawerMenuItem(
            R.id.profileFragment,
            R.string.profile_destination_name,
        )
    )
}
