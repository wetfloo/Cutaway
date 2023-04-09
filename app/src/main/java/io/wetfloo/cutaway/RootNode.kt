package io.wetfloo.cutaway

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.backstack.BackStack
import io.wetfloo.cutaway.feature.user_profile.ProfileNode

class RootNode(
    buildContext: BuildContext,
    backStack: BackStack<FeatureNavigationTarget> = BackStack(
        initialElement = FeatureNavigationTarget.FeatureProfile,
        savedStateMap = buildContext.savedStateMap,
    )
) : ParentNode<FeatureNavigationTarget>(
    buildContext = buildContext,
    navModel = backStack,
) {
    override fun resolve(navTarget: FeatureNavigationTarget, buildContext: BuildContext): Node {
        return when (navTarget) {
            FeatureNavigationTarget.FeatureProfile -> node(buildContext) {
                ProfileNode(buildContext)
            }
        }
    }
}
