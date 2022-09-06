package com.hrudhaykanth116.mafet.auth.ui.effects

sealed class SignUpEffect{
    data class Success(val message: String? = null): SignUpEffect()
    data class Error(val message: String? = null): SignUpEffect()
}