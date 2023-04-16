package com.hrudhaykanth116.mafet.auth.data.models

import android.graphics.Bitmap

class SignUpRequest(
    val email: String,
    val password: String,
    val userName: String?,
    // These are optional now
    val imgBitmap: Bitmap?,
    val bio: String?,
)