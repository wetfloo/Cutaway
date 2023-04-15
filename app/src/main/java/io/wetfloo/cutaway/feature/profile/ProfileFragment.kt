package io.wetfloo.cutaway.feature.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.ComposeFragment

@AndroidEntryPoint
class ProfileFragment : ComposeFragment() {
    override fun ComposeView.compose() {
        setContent {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Text("Profile")
            }
        }
    }
}
