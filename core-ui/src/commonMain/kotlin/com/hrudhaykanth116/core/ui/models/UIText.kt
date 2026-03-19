package com.hrudhaykanth116.core.ui.models

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.platform.LocalContext

/**
 * Wrapper class for raw text or string resource. This helps in having single data type when displaying text on UI.
 * For example if the name(String) from domain layer is null, we may show string resource.
 */
@Immutable
sealed class UIText {

    class StringRes(@androidx.annotation.StringRes val stringRes: Int, vararg val formatArgs: Any) : UIText()
    data class Text(val rawString: String): UIText()

    @Composable
    fun getText(): String{
        return when(this){
            is StringRes -> {
                LocalContext.current.getString(stringRes, formatArgs)
            }
            is Text -> {
                rawString
            }
        }
    }

    fun getText(context: Context): String{
        return when(this){
            is StringRes -> {
                context.getString( stringRes, formatArgs)
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
    this ?: return UIText.Text(ifNullString)

    return toString().toUIText()

}