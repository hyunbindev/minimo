package com.hyunbindev.minimo.model.application

import com.hyunbindev.minimo.model.application.ApplicationTable.applicationName
import com.hyunbindev.minimo.model.application.ApplicationTable.descriptionName
import com.hyunbindev.minimo.model.application.ApplicationTable.iconPath
import com.hyunbindev.minimo.model.memo.MemoTable
import javafx.scene.image.Image
import org.jetbrains.exposed.sql.ResultRow

data class ApplicationData(
    val pid:Long?=null,
    val applicationName:String?,
    val descriptionName:String?,
    val iconPath: String?
){
    companion object{
        fun fromTable(row: ResultRow): ApplicationData{
            return ApplicationData(
                applicationName = row[applicationName],
                descriptionName = row[descriptionName],
                iconPath = row[iconPath],
            )
        }
    }
}