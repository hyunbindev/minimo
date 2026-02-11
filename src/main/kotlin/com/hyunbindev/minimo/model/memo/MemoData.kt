package com.hyunbindev.minimo.model.memo

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.clipboard.ClipType
import com.hyunbindev.minimo.model.memo.MemoTable.content
import com.hyunbindev.minimo.model.memo.MemoTable.fixed
import com.hyunbindev.minimo.model.memo.MemoTable.hash
import com.hyunbindev.minimo.model.memo.MemoTable.id
import com.hyunbindev.minimo.model.memo.MemoTable.tabId
import com.hyunbindev.minimo.model.memo.MemoTable.type
import com.hyunbindev.minimo.model.memo.MemoTable.updatedAt
import javafx.scene.image.Image
import org.jetbrains.exposed.sql.ResultRow
import java.time.LocalDateTime

data class MemoData(
    val id:Int? = null,
    val tabId:Int,
    val type: ClipType,
    val fixed:Boolean = false,
    val content:String,
    val updatedAt: LocalDateTime? = LocalDateTime.now(),
    val hash:String,

){
    companion object{
        fun fromClipboard(clip: ClipBoardData, tabId: Int): MemoData{
            return MemoData(
                tabId = tabId,
                type = clip.type,
                content = clip.stringContent,
                hash = clip.contentHash
            )
        }

        fun fromTable(row: ResultRow): MemoData{
            return MemoData(
                id=row[id],
                tabId = row[tabId],
                type = ClipType.valueOf(row[type].uppercase()),
                fixed=row[fixed],
                content = row[content],
                updatedAt = row[updatedAt],
                hash = row[hash]
            )
        }
    }
}