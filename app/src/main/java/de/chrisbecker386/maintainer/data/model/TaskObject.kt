package de.chrisbecker386.maintainer.data.model

import androidx.annotation.DrawableRes

data class TaskObject(
    val id: Int,
    val title: String,
    @DrawableRes
    val graphic: Int? = null,
    val steps: List<StepObject> = emptyList()
)
