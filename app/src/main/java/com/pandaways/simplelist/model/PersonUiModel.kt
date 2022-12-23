package com.pandaways.simplelist.model

import androidx.annotation.DrawableRes
import java.util.Date

data class PersonUiModel(
    @DrawableRes val imageRes: Int,
    val name: String,
    val birthDate: Date,
    val sex: String,
    val address: String,
)
