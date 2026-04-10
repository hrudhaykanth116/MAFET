package com.hrudhaykanth116.core.common.utils.url

/**
 * Matches any string that has a valid URI scheme (RFC 3986).
 *
 * Covers:
 *   - Web URLs:   https://example.com, http://example.com
 *   - Email:      mailto:user@example.com
 *   - Phone:      tel:+1234567890
 *   - Maps:       geo:37.77,-122.41
 *   - Play Store: market://details?id=com.example
 *   - Deep links: myapp://screen/detail
 *   - Android:    intent://...
 *
 * Does NOT match plain strings like "dismiss", "some_key", or bare
 * hostnames like "play.google.com/store" (no scheme → not a URI).
 */
private val URI_SCHEME_REGEX = Regex("^[a-zA-Z][a-zA-Z0-9+\\-.]*:.*")

fun String.isUrl(): Boolean = URI_SCHEME_REGEX.matches(this)
