package io.wetfloo.cutaway.ui.core

import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.core.model.DrawerDestination

val destinations: Array<DrawerDestination> by lazy(LazyThreadSafetyMode.NONE) {
    arrayOf(
        DrawerDestination(
            textId = R.string.profile_destination_name,
            id = R.id.profileFragment,
        ),
        DrawerDestination(
            textId = R.string.search_user_destination_name,
            id = R.id.searchUserFragment,
        )
    )
}
