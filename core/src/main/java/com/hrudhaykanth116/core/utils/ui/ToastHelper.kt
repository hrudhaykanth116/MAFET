package com.hrudhaykanth116.core.utils.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.hrudhaykanth116.core.R
import com.hrudhaykanth116.core.databinding.LayoutCustomToastBinding

object ToastHelper {

    // Can be used to cancel existing toast before showing new toast.
    fun show(context: Context, msg: com.hrudhaykanth116.core.data.models.UIText) {
        val toast = Toast.makeText(context, msg.getText(context), Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showWarningToast(
        context: Context,
        msg: com.hrudhaykanth116.core.data.models.UIText? = null,
    ) {
        msg ?: return
        val customToast = getCustomToast(
            context,
            msg.getText(context),
            R.drawable.bg_toast_orange,
            R.drawable.ic_warning
        )
        customToast.show()
    }

    fun showSuccessToast(
        context: Context,
        msg: com.hrudhaykanth116.core.data.models.UIText? = null,
    ) {
        msg ?: return
        val customToast = getCustomToast(
            context,
            msg.getText(context),
            R.drawable.bg_toast_green,
            R.drawable.ic_check
        )
        customToast.show()
    }

    fun showErrorToast(
        context: Context,
        msg: com.hrudhaykanth116.core.data.models.UIText? = null
    ) {
        msg ?: return
        val customToast = getCustomToast(
            context,
            msg.getText(context),
            R.drawable.bg_toast_red,
            R.drawable.ic_check
        )
        customToast.show()
    }

    private fun getCustomToast(
        context: Context,
        toastMsg: String?,
        @DrawableRes bgDrawable: Int,
        @DrawableRes drawableStart: Int,
    ): Toast {
        return Toast(context).also {
            val layoutInflater = LayoutInflater.from(context)
            val binding = LayoutCustomToastBinding.inflate(layoutInflater)
            binding.root.background = ContextCompat.getDrawable(context, bgDrawable)
            val toastText = binding.textviewToast
            binding.textviewToast
            binding.textviewToast.text = toastMsg
            binding.textviewToast.setCompoundDrawablesWithIntrinsicBounds(drawableStart, 0, 0, 0)
            it.view = binding.root
            it.duration = Toast.LENGTH_LONG
        }
    }
}