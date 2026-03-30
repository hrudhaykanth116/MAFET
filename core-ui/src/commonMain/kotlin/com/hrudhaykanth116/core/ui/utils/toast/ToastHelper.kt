package com.hrudhaykanth116.core.ui.utils.toast

// TODO: kmp use this
object ToastHelper {
    //
    // // Can be used to cancel existing toast before showing new toast.
    // fun show(context: Context, msg: UIText) {
    //     val toast = Toast.makeText(context, msg.getText(context), Toast.LENGTH_SHORT)
    //     toast.show()
    // }
    //
    // fun showWarningToast(
    //     context: Context,
    //     msg: UIText? = null,
    // ) {
    //     msg ?: return
    //     val customToast = getCustomToast(
    //         context,
    //         msg.getText(context),
    //         R.drawable.bg_toast_orange,
    //         R.drawable.ic_warning
    //     )
    //     customToast.show()
    // }
    //
    // fun showSuccessToast(
    //     context: Context,
    //     msg: UIText? = null,
    // ) {
    //     msg ?: return
    //     val customToast = getCustomToast(
    //         context,
    //         msg.getText(context),
    //         R.drawable.bg_toast_green,
    //         R.drawable.ic_check
    //     )
    //     customToast.show()
    // }
    //
    // fun showErrorToast(
    //     context: Context,
    //     msg: UIText? = null
    // ) {
    //     msg ?: return
    //     showErrorToast(context, msg.getText(context))
    //
    // }
    //
    // fun showErrorToast(
    //     context: Context,
    //     msg: String
    // ) {
    //     val customToast = getCustomToast(
    //         context,
    //         msg,
    //         R.drawable.bg_toast_red,
    //         R.drawable.ic_check
    //     )
    //     customToast.show()
    // }
    //
    // private fun getCustomToast(
    //     context: Context,
    //     toastMsg: String?,
    //     @DrawableRes bgDrawable: Int,
    //     @DrawableRes drawableStart: Int,
    // ): Toast {
    //     return Toast(context).also {
    //         val layoutInflater = LayoutInflater.from(context)
    //         val binding = LayoutCustomToastBinding.inflate(layoutInflater)
    //         binding.root.background = ContextCompat.getDrawable(context, bgDrawable)
    //         val toastText = binding.textviewToast
    //         binding.textviewToast
    //         binding.textviewToast.text = toastMsg
    //         binding.textviewToast.setCompoundDrawablesWithIntrinsicBounds(drawableStart, 0, 0, 0)
    //         it.view = binding.root
    //         it.duration = Toast.LENGTH_SHORT
    //     }
    // }
    //
    // fun show(context: Context, userMessage: UserMessage) {
    //     when (userMessage) {
    //         is UserMessage.Error -> showErrorToast(context, userMessage.message)
    //         is UserMessage.Success -> showSuccessToast(context, userMessage.message)
    //         is UserMessage.Warning -> showWarningToast(context, userMessage.message)
    //     }
    // }

}