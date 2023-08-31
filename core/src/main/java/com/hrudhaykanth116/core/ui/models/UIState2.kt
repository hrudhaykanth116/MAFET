package com.hrudhaykanth116.core.ui.models

sealed class UIState2(
) {

    object Loading : UIState2()

    object Error : UIState2()

    object Idle : UIState2()

}



