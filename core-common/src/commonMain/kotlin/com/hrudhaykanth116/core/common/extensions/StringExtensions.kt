package com.hrudhaykanth116.core.common.extensions

/**
 * Returns true if the string is a valid email address.
 * Basic email validation using regex.
 */
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    return emailRegex.matches(this)
}

/**
 * Capitalizes the first letter of the string.
 */
fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

/**
 * Returns true if string is not null and not blank.
 */
fun String?.isNotNullOrBlank(): Boolean {
    return !this.isNullOrBlank()
}

/**
 * Truncates the string to the specified length and adds ellipsis if needed.
 */
fun String.truncate(maxLength: Int, ellipsis: String = "..."): String {
    return if (this.length <= maxLength) this
    else this.substring(0, maxLength - ellipsis.length) + ellipsis
}
