package de.chrisbecker386.maintainer.data.model

import androidx.annotation.DrawableRes

data class CareObject(
    val id: Int,
    val title: String,
    @DrawableRes
    val graphic: Int? = null,
    val maintainObjects: List<MaintainObject> = emptyList()
)
