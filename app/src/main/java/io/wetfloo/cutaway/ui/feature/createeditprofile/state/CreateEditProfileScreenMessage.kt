package io.wetfloo.cutaway.ui.feature.createeditprofile.state

import android.net.Uri
import io.wetfloo.cutaway.data.model.profile.ProfileInformation

sealed interface CreateEditProfileScreenMessage {
    object Save : CreateEditProfileScreenMessage

    object GoBack : CreateEditProfileScreenMessage

    data class UpdateProfile(val profileInformation: ProfileInformation) : CreateEditProfileScreenMessage

    data class ImagePicked(val imageUri: Uri) : CreateEditProfileScreenMessage
}
