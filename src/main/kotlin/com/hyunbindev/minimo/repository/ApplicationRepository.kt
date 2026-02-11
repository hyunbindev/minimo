package com.hyunbindev.minimo.repository

import com.hyunbindev.minimo.model.application.ApplicationData
import com.hyunbindev.minimo.model.application.ApplicationTable
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object ApplicationRepository {
    fun createApplication(applicationData: ApplicationData):ApplicationData = transaction {
        val insertStatement = ApplicationTable.insert{
            if(applicationData.applicationName != null){
                it[applicationName] = applicationData.applicationName
                it[descriptionName] = applicationData.descriptionName
                it[iconPath] = applicationData.iconPath
            }else{
                throw NullPointerException("The application name cannot be null")
            }
        }
        val row = insertStatement.resultedValues?.firstOrNull()
            ?: throw IllegalStateException("fail to save application meta data")

        ApplicationData.fromTable(row)
    }

    fun getApplication(applicationName:String):ApplicationData? = transaction {
        ApplicationTable.selectAll()
            .where(ApplicationTable.applicationName eq applicationName)
            .limit(1)
            .map{ ApplicationData.fromTable(it) }
            .singleOrNull()
    }
}