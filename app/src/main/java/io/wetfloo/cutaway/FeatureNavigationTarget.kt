package io.wetfloo.cutaway

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed interface FeatureNavigationTarget : Parcelable {
    @Parcelize
    object FeatureProfile : FeatureNavigationTarget
}
