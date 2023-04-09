package io.wetfloo.cutaway

import android.os.Bundle
import androidx.activity.compose.setContent
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import io.wetfloo.cutaway.core.ui.compose.core.AppTheme

class MainActivity : NodeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NodeHost(
                    integrationPoint = appyxIntegrationPoint,
                    factory = ::RootNode,
                )
            }
        }
    }
}
