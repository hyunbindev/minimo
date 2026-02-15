package com.hyunbindev.minimo.model.application

import com.hyunbindev.minimo.model.application.ApplicationTable.applicationName
import com.hyunbindev.minimo.model.application.ApplicationTable.descriptionName
import com.hyunbindev.minimo.model.application.ApplicationTable.iconPath
import com.hyunbindev.minimo.model.memo.MemoTable
import javafx.scene.image.Image
import org.jetbrains.exposed.sql.ResultRow

/**
 * TODO- 어플리케이션 아이콘은 저장된 어플리케이션의 경로 기반으로 가져온다
 * 이후 컴포넌트 생성(MEMO ELEMENT ) 생성시 어플리케이션 경로 에서 가져온다....
 */
data class ApplicationData(
    val pid:Long?,
    val applicationName:String?=null,
    val descriptionName:String?=null,
    val iconPath: String?=null
){
    companion object{
        fun fromTable(row: ResultRow): ApplicationData{
            return ApplicationData(
                applicationName = row[applicationName],
                descriptionName = row[descriptionName],
                iconPath = row[iconPath],
                pid = null,
            )
        }
    }
}