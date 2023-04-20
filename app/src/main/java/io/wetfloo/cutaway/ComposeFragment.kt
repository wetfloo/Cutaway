package io.wetfloo.cutaway

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import io.wetfloo.cutaway.core.ui.compose.core.AppTheme
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.component.TransparentSystemBars

abstract class ComposeFragment : Fragment(R.layout.fragment_compose_base) {
    private var binding: FragmentComposeBaseBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentComposeBaseBinding.bind(view)

        binding?.composeView?.apply {
            setViewCompositionStrategy(
                strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
            )
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected fun drawContent(content: @Composable () -> Unit) {
        binding?.composeView?.apply {
            setContent {
                TransparentSystemBars()
                AppTheme {
                    content()
                }
            }
        }
    }
}
