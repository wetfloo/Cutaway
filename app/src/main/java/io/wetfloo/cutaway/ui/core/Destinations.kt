package io.wetfloo.cutaway.ui.core

import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.core.common.lazyUnsafe
import io.wetfloo.cutaway.ui.core.model.DrawerDestination

val destinations: Array<DrawerDestination> by lazyUnsafe {
    arrayOf(
        DrawerDestination(
            textId = R.string.profile_destination_name,
            id = R.id.profileFragment,
        ),
        DrawerDestination(
            textId = R.string.search_profile_destination_name,
            id = R.id.searchProfileFragment,
        ),
    )
}
