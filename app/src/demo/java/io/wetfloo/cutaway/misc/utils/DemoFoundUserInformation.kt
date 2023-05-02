package io.wetfloo.cutaway.misc.utils

import io.wetfloo.cutaway.core.common.randomAlphaNumericString
import io.wetfloo.cutaway.data.model.searchuser.FoundUser

val FoundUser.Companion.demo
    get() = FoundUser(randomAlphaNumericString(16))
