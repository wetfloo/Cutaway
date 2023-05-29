package io.wetfloo.cutaway.ui.feature.createeditprofile.state

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.data.model.profile.ProfileInformation
import kotlinx.parcelize.Parcelize

@Immutable
sealed interface CreateEditMode : Parcelable {
    @get:StringRes
    val nameRes: Int

    @Parcelize
    data class Edit(
        val profileInformation: ProfileInformation,
    ) : CreateEditMode {
        override val nameRes
            get() = R.string.create_edit_profile_destination_name_edit
    }

    @Parcelize
    object Create : CreateEditMode {
        override val nameRes
            get() = R.string.create_edit_profile_destination_name_create
    }
}
