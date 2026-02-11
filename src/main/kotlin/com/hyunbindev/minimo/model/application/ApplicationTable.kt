package com.hyunbindev.minimo.model.application

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.Table

object ApplicationTable : Table("application"){
    val applicationName = text("applicationName")
    val descriptionName = text("descriptionName").nullable()
    val iconPath = text("iconPath").nullable()

    override val primaryKey = PrimaryKey(applicationName)
}