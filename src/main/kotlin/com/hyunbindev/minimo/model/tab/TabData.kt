package com.hyunbindev.minimo.model.tab

import java.time.LocalDateTime

data class TabData(
    var index: Int,
    var title: String,
    val id:Int,
    var updatedAt: LocalDateTime,
) {
}