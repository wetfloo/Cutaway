package io.wetfloo.cutaway.core.commonimpl

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.core.common.eventflow.EventFlow
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow

typealias MutableEventResultFlow<T> = MutableEventFlow<Result<T, UiError>>
typealias EventResultFlow<T> = EventFlow<Result<T, UiError>>
