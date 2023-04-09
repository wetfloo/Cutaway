package io.wetfloo.cutaway

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node

class RootNode(
    buildContext: BuildContext,
) : Node(
    buildContext = buildContext,
) {
    @Composable
    override fun View(modifier: Modifier) {
        Text("Hello world!")
    }
}
