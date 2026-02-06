package com.hyunbindev.minimo.model

import com.hyunbindev.minimo.model.tab.TabTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init() {
        // 프로젝트 루트에 minimo.db 파일 생성
        Database.connect("jdbc:sqlite:./minimo.db", driver = "org.sqlite.JDBC")

        // 테이블 생성 및 업데이트 (단수형 TabTable 사용)
        transaction {
            SchemaUtils.create(TabTable)
        }
    }
}