package com.hrudhaykanth116.mafet.auth.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(
    val userName: String? = null,
    val bio: String? = null,
    val profileImgUrl: String? = null,
): Parcelable