package com.hrudhaykanth116.core.common.extensions

/**
 * Returns true if the collection is not null and not empty.
 */
fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty()
}

/**
 * Returns the collection if not empty, otherwise returns null.
 */
fun <T> Collection<T>.ifEmpty(default: Collection<T>): Collection<T> {
    return if (this.isEmpty()) default else this
}

/**
 * Safe get element at index, returns null if index is out of bounds.
 */
fun <T> List<T>.getOrNull(index: Int): T? {
    return if (index in indices) this[index] else null
}
