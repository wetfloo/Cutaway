package io.wetfloo.cutaway.ui.core

import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.core.model.DrawerDestination

val destinations: Array<out DrawerDestination> by lazy {
    arrayOf(
        DrawerDestination.Identifiable(
            R.id.profileFragment,
            R.string.profile_destination_name,
        ),
        DrawerDestination.Identifiable(
            R.id.qrFragment,
            R.string.qr_scanner_destination_name,
        ),
        DrawerDestination.Identifiable(
            R.id.searchUserFragment,
            R.string.search_user_destination_name,
        )
    )
}
