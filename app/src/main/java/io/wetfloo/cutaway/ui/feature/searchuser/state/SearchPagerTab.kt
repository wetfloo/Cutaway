package io.wetfloo.cutaway.ui.feature.searchuser.state

import androidx.annotation.StringRes
import io.wetfloo.cutaway.R

enum class SearchPagerTab(@StringRes val stringRes: Int) {
    SEARCH(R.string.search_user_tab_search),
    HISTORY(R.string.search_user_tab_history),
}
