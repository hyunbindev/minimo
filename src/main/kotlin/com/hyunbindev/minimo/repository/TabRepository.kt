package com.hyunbindev.minimo.repository

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.model.tab.TabTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object TabRepository {
    /**
     * # create new tab
     */
    fun createNewTab(tabIdx: Int): TabData = transaction {
        val insertStatement = TabTable.insert {
            it[index] = tabIdx
            it[title] = "New tab $tabIdx"
            it[updatedAt] = LocalDateTime.now()
        }
        TabData(
            id = insertStatement[TabTable.id],
            index = insertStatement[TabTable.index],
            title = insertStatement[TabTable.title],
            updatedAt = insertStatement[TabTable.updatedAt]
        )
    }
    fun findAllTabs():List<TabData> = transaction {
        TabTable.selectAll()
            .orderBy(TabTable.updatedAt to SortOrder.ASC)
            .map {
                TabData(
                id        = it[TabTable.id],
                index     = it[TabTable.index],
                title     = it[TabTable.title],
                updatedAt = it[TabTable.updatedAt]
                )
            }
    }
    fun deleteTab(tabId: Int)=transaction {
        TabTable.deleteWhere { TabTable.id eq tabId }
    }
}