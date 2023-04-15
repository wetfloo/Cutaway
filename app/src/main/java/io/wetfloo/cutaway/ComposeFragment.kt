package io.wetfloo.cutaway

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding

abstract class ComposeFragment : Fragment(R.layout.fragment_compose_base) {
    private var _binding: FragmentComposeBaseBinding? = null
    protected val binding: FragmentComposeBaseBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComposeBaseBinding.inflate(
            /* inflater = */
            inflater,
            /* parent = */
            container,
            /* attachToParent = */
            false,
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(
                strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
