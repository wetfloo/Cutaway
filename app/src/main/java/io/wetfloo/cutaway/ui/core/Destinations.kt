package io.wetfloo.cutaway.ui.core

import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.component.DrawerMenuItem

val destinations: List<DrawerMenuItem> by lazy {
    listOf(
        DrawerMenuItem(
            R.id.profileFragment,
            R.string.profile_destination_name,
        ),
        DrawerMenuItem(
            R.id.qrFragment,
            R.string.qr_scanner_destination_name,
        ),
        DrawerMenuItem(
            R.id.searchUserFragment,
            R.string.search_user_destination_name,
        )
    )
}
