package io.wetfloo.cutaway.ui.feature.profiledetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.profiledetails.state.ProfileDetailedScreenMessage

@AndroidEntryPoint
class ProfileDetailedInformationFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val args: ProfileDetailedInformationFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.composify {
            ProfileDetailedInformationScreen(
                data = args.profileInformation,
                onMessage = { message ->
                    when (message) {
                        ProfileDetailedScreenMessage.GoBack -> findNavController().popBackStack()
                    }
                }
            )
        }
    }
}
