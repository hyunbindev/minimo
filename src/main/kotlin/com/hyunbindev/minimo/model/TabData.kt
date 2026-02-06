package com.hyunbindev.minimo.model

import java.time.LocalDateTime

data class TabData(
    var index: Int,
    var title: String,
) {
    val id:Int? = null
    var updatedAt: LocalDateTime = LocalDateTime.now()
}