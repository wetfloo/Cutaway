package io.wetfloo.cutaway.ui.core

import androidx.navigation.NavController

val NavController.activeDestinationId: Int?
    get() = currentDestination?.id
