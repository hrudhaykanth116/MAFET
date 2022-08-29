package com.hrudhaykanth116.mafet.common.utils.locale

import android.content.Context
import androidx.annotation.StringRes

object LocaleHelper {

    fun getString(
        context: Context,
        @StringRes stringId: Int,
        vararg formatArgs: Any?
    ): String {
        return context.getString(stringId, *formatArgs)
    }

}