package com.hyunbindev.minimo.repository

import com.hyunbindev.minimo.model.memo.MemoData
import com.hyunbindev.minimo.model.memo.MemoTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object MemoRepository {
    fun createNewMemo(memoData: MemoData): MemoData = transaction {
        val insertStatement: InsertStatement<Number> = MemoTable.insert {
            it[tabId] = memoData.tabId
            it[fixed] = memoData.fixed
            it[content] = memoData.content
            it[type] = memoData.type.name
            it[isDeleted] = false
            it[updatedAt] = LocalDateTime.now()
            it[hash] = memoData.hash
        }
        val row = insertStatement.resultedValues?.firstOrNull()
            ?: throw IllegalStateException("데이터 저장에 실패했습니다.")
        MemoData.fromTable(row)
    }

    fun getMemoByTabId(tabId: Int): List<MemoData> = transaction {
        MemoTable.selectAll()
            .andWhere { MemoTable.tabId eq tabId }
            .andWhere { MemoTable.isDeleted eq false }
            .orderBy( MemoTable.updatedAt to SortOrder.DESC )
            .map { MemoData.fromTable(it) }
    }
}