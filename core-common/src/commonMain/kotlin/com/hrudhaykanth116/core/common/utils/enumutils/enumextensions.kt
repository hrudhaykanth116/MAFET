package com.hrudhaykanth116.core.common.utils.enumutils

inline fun <reified T : Enum<T>> String?.asEnumOrDefault(defaultValue: T): T =
    enumValues<T>().firstOrNull { it.name.equals(this, ignoreCase = true) } ?: defaultValue