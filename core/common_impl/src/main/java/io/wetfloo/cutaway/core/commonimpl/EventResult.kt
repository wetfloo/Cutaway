package io.wetfloo.cutaway.core.commonimpl

import com.github.michaelbull.result.Result
import io.wetfloo.cutaway.core.common.eventflow.EventFlow
import io.wetfloo.cutaway.core.common.eventflow.MutableEventFlow

typealias EventResult<T> = Result<T, UiError>
typealias MutableEventResultFlow<T> = MutableEventFlow<EventResult<T>>
typealias EventResultFlow<T> = EventFlow<EventResult<T>>
