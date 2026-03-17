package com.hrudhaykanth116.shared

expect class Platform() {
    val name: String
}

fun getPlatformName(): String = Platform().name
