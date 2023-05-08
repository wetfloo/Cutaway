package io.wetfloo.cutaway.ui.feature.searchuser

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.databinding.FragmentComposeBaseBinding
import io.wetfloo.cutaway.ui.core.composify
import io.wetfloo.cutaway.ui.feature.searchuser.state.SearchUserMessage

@AndroidEntryPoint
class SearchUserFragment : Fragment(R.layout.fragment_compose_base) {
    private val binding by viewBinding(FragmentComposeBaseBinding::bind)
    private val viewModel: SearchUserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeView.composify {
            val state by viewModel
                .stateFlow
                .collectAsStateWithLifecycle()

            val searchHistoryState by viewModel
                .searchHistoryState
                .collectAsStateWithLifecycle()

            SearchUserScreen(
                state = state,
                searchHistoryState = searchHistoryState,
                errorFlow = viewModel.error,
                navController = { findNavController() },
                onQueryChange = viewModel::updateQuery,
                queryValue = viewModel.query,
                onMessage = { message ->
                    when (message) {
                        SearchUserMessage.SearchRequested -> viewModel.search()

                        SearchUserMessage.ClearHistory -> viewModel.clearHistory()

                        is SearchUserMessage.DeleteHistoryItem -> {
                            viewModel.deleteHistoryItem(message.item)
                        }

                        is SearchUserMessage.FoundUserClicked -> {
                            // TODO: implement navigation here
                        }
                    }
                },
            )
        }
    }

}
