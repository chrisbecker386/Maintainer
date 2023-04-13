package de.chrisbecker386.maintainer.data.model

import androidx.annotation.DrawableRes

data class MaintainObject(
    val id: Int,
    val title: String,
    @DrawableRes
    val graphic: Int? = null,
    val tasks: List<TaskObject> = emptyList()
)
