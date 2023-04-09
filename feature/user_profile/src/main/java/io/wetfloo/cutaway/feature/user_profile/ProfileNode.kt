package io.wetfloo.cutaway.feature.user_profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import io.wetfloo.cutaway.feature.user_profile.ui.Profile

class ProfileNode(
    buildContext: BuildContext,
) : Node(buildContext) {
    @Composable
    override fun View(modifier: Modifier) {
        Profile()
    }
}
