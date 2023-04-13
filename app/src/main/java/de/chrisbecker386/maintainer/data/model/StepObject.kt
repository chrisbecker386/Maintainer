package de.chrisbecker386.maintainer.data.model

import androidx.annotation.DrawableRes

data class StepObject(
    val id: Int,
    val orderNumber: Int,
    val title: String,
    val description: String? = null,
    @DrawableRes
    val graphic: Int? = null
)
