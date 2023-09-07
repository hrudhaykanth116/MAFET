package com.hrudhaykanth116.core.data.models

import android.content.Context
import androidx.compose.runtime.Composable
import com.hrudhaykanth116.core.common.utils.locale.LocaleHelper
import com.hrudhaykanth116.core.common.utils.string.replaceIfBlank

sealed class UIText {

    class StringRes(@androidx.annotation.StringRes val stringRes: Int, vararg val formatArgs: Any) : UIText()
    data class Text(val rawString: String): UIText()

    @Composable
    fun getText(): String{
        return when(this){
            is StringRes -> {
                LocaleHelper.getString(stringRes, formatArgs)
            }
            is Text -> {
                rawString
            }
        }
    }

    fun getText(context: Context): String{
        return when(this){
            is StringRes -> {
                LocaleHelper.getString(context, stringRes, formatArgs)
            }
            is Text -> {
                rawString
            }
        }
    }
}

fun String.toUIText(): UIText.Text {
    return UIText.Text(this)
}

fun Any?.toUIText(ifNullString: String): UIText{

    return toString().replaceIfBlank(ifNullString).toUIText()

}