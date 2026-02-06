package com.hyunbindev.minimo.model.tab

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object TabTable : Table("tab") {
    val id = integer("id").autoIncrement()
    val index = integer(name = "index")
    val title = varchar("title", 255)
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())

    override val primaryKey = PrimaryKey(id)
}