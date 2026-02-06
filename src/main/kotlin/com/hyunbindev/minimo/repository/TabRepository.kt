package com.hyunbindev.minimo.repository

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.model.tab.TabTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object TabRepository {
    /**
     * # create new tab
     */
    fun createNewTab(tabIdx: Int): TabData = transaction {
        // 1. 여기서 커넥션을 열고 쿼리를 날립니다.
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
}