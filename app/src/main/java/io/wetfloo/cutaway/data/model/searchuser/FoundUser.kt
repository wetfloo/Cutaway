package io.wetfloo.cutaway.data.model.searchuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoundUser(val name: String) : Parcelable {
    /**
     * For static extensions
     */
    companion object
}
