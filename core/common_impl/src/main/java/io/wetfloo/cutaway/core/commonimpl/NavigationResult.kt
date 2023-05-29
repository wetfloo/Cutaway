package io.wetfloo.cutaway.core.commonimpl

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import kotlinx.parcelize.Parcelize

/**
 * Saves some [value] retrievable by [key] into SavedStateHandle for previous Fragment to consume,
 * if such backStack entry is present.
 */
fun <T : Parcelable> Fragment.setNavigationResult(
    key: String,
    value: T,
) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(
        key = key,
        value = NavigationResult.Data(value),
    )
}

/**
 * Passes some data left for this [Fragment] by the next one to [onResult],
 * using [key] [String].
 *
 * Note that if [onResult] gets passed null, it generally means that
 * next Fragment decided to leave no data for caller Fragment.
 *
 * You should **never** make your UI events wait for non-empty values being passed
 * to [onResult] callback, handle those values gracefully.
 */
fun <T : Parcelable> Fragment.getNavigationResult(
    key: String,
    onResult: (NavigationResult<T>) -> Unit,
) {
    /*

    The following lifecycle observer will be triggered when navigated to the next Fragment,
    so that's why we get previousBackStackEntry here instead of currentBackStackEntry.

    For example:
        A -> B (current back stack entry is B)

    But we need to get a result at Fragment A from Fragment B,
    and that's why we wait until it's at least in RESUMED state to trigger onResult callback.

    Fragments A and B will be referenced further in this function's comments.

    */
    val navBackStackEntry = findNavController().previousBackStackEntry
    if (navBackStackEntry == null) {
        onResult(NavigationResult.Empty)
        return
    }

    val previousBackStackEntryObserver = LifecycleEventObserver { _, event ->
        // making sure that our Fragment A is in RESUMED state to trigger UI updates safely
        if (event == Lifecycle.Event.ON_RESUME) {
            // get that SavedStateHandle left there by Fragment B in Fragment A
            val result: NavigationResult<T> = navBackStackEntry.savedStateHandle[key] ?: NavigationResult.Empty
            // trigger that callback
            onResult(result)
            // we don't want to data in SavedStateHandle
            //  to render updates every time Fragment A is in RESUMED state
            navBackStackEntry.savedStateHandle.remove<T>(key)
        }
    }
    navBackStackEntry.getLifecycle().addObserver(previousBackStackEntryObserver)

    viewLifecycleOwner.lifecycle.addObserver(
        LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                // stop observing if Fragment is in DESTROYED state
                navBackStackEntry.getLifecycle().removeObserver(previousBackStackEntryObserver)
            }
        },
    )
}

sealed interface NavigationResult<out T : Parcelable> : Parcelable {
    @Parcelize
    object Empty : NavigationResult<Nothing>

    @Parcelize
    data class Data<out T : Parcelable>(val value: T) : NavigationResult<T>
}
