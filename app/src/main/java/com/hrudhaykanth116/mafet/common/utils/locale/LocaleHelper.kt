package com.hrudhaykanth116.mafet.common.utils.locale

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

object LocaleHelper {

    fun getString(
        context: Context,
        @StringRes stringId: Int,
        vararg formatArgs: Any
    ): String {
        return context.getString(stringId, *formatArgs)
    }

    @Composable
    fun getString(
        @StringRes stringId: Int,
        vararg formatArgs: Any
    ): String {
        return stringResource(id = stringId, formatArgs = formatArgs)
    }

}