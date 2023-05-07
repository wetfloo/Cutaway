package io.wetfloo.cutaway.ui.feature.searchuser.state

import androidx.annotation.StringRes
import io.wetfloo.cutaway.R
import io.wetfloo.cutaway.ui.core.model.KeyboardVisibilityAction

enum class SearchPagerTab(
    @StringRes val stringRes: Int,
    val keyboardVisibilityAction: KeyboardVisibilityAction,
) {
    SEARCH(
        stringRes = R.string.search_user_tab_search,
        keyboardVisibilityAction = KeyboardVisibilityAction.SHOW,
    ),
    HISTORY(
        stringRes = R.string.search_user_tab_history,
        keyboardVisibilityAction = KeyboardVisibilityAction.HIDE,
    ),
}
