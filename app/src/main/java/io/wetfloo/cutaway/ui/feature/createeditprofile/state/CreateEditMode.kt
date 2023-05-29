package io.wetfloo.cutaway.ui.feature.createeditprofile.state

import androidx.annotation.StringRes
import io.wetfloo.cutaway.R

enum class CreateEditMode(@StringRes val nameRes: Int) {
    EDIT(R.string.create_edit_profile_destination_name_edit),
    CREATE(R.string.create_edit_profile_destination_name_create),
}
