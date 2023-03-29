package com.hrudhaykanth116.mafet.auth.ui.screens.signup

import com.hrudhaykanth116.mafet.common.data.models.UIText

sealed class SignUpEffect{
    data class Success(val message: UIText? = null): SignUpEffect()
    data class Error(val message: UIText? = null): SignUpEffect()
}