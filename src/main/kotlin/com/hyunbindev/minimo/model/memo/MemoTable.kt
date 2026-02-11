package com.hyunbindev.minimo.model.memo

import com.hyunbindev.minimo.model.application.ApplicationTable
import com.hyunbindev.minimo.model.tab.TabTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object MemoTable : Table("memo"){
    val id = integer("id").autoIncrement()
    val tabId = integer("tabId") references TabTable.id
    val fixed = bool("fixed").default(false)
    val content = text("content")
    val type = varchar("type",50)
    val isDeleted = bool("deleted").default(false)
    val updatedAt = datetime("updated_at").default(LocalDateTime.now())
    val hash = varchar("hash", 64)



    val applicationName = text("applicationName")
        .references(ApplicationTable.applicationName)
        .nullable()

    override val primaryKey = PrimaryKey(id)

    val memoIndex = index(isUnique = false, tabId, isDeleted, fixed)
}