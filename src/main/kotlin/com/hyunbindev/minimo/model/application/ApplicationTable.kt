package com.hyunbindev.minimo.model.application

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ApplicationTable : Table("application"){
    val applicationName = text("applicationId")
    val applicationDescriptionName = text("applicationDescriptionName")
    val iconPath = text("iconPath")
}