package com.hrudhaykanth116.core.ui.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * Wrapper class for raw text or string resource. This helps in having single data type when displaying text on UI.
 * For example if the name(String) from domain layer is null, we may show string resource.
 */
@Immutable
sealed class UIText {

    class StringRes(val stringRes: StringResource, vararg val formatArgs: Any) : UIText()
    data class Text(val rawString: String): UIText()

    @Composable
    fun getText(): String{
        return when(this){
            is StringRes -> {
                stringResource(stringRes, formatArgs)
            }
            is Text -> {
                rawString
            }
        }
    }

    // fun getText(context: Context): String{
    //     return when(this){
    //         is StringRes -> {
    //             context.getString( stringRes, formatArgs)
    //         }
    //         is Text -> {
    //             rawString
    //         }
    //     }
    // }
}

fun String.toUIText(): UIText.Text {
    return UIText.Text(this)
}

fun StringResource.toUIText(vararg formatArgs: Any): UIText.StringRes {
    return UIText.StringRes(this, *formatArgs)
}

fun Any?.toUIText(ifNullString: String): UIText{
    this ?: return UIText.Text(ifNullString)

    return toString().toUIText()

}