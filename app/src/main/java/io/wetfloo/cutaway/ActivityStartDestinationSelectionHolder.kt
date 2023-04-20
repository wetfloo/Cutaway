package io.wetfloo.cutaway

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class ActivityStartDestinationSelectionHolder @Inject constructor() {
    var isStartingDestinationSelected = false
}
