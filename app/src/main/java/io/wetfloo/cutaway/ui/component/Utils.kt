package io.wetfloo.cutaway.ui.component

import androidx.lifecycle.SavedStateHandle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <V> SavedStateHandle.saved(
    default: V,
    key: String? = null,
) = object : ReadWriteProperty<Any?, V> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): V =
        get(key(property.name)) ?: default

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        set(
            key = key(property.name),
            value = value,
        )
    }

    private fun key(propertyName: String) = key ?: propertyName
}
