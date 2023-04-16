package io.wetfloo.cutaway

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import io.wetfloo.cutaway.core.ui.compose.core.AppTheme
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding

abstract class ComposeFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(
                strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
            )
        }
    }

    protected fun drawContent(content: @Composable () -> Unit) {
        binding.composeView.apply {
            setContent {
                AppTheme {
                    content()
                }
            }
        }
    }
}
