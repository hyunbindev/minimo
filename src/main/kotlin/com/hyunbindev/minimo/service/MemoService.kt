package com.hyunbindev.minimo.service

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.memo.MemoData
import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.repository.MemoRepository
import org.jetbrains.exposed.sql.transactions.transaction

object MemoService {

    fun createMemo(memoData: MemoData):MemoData{
        return MemoRepository.createNewMemo(memoData)
    }

    fun getMemoByTabId(tabId: Int): List<MemoData> {
        return MemoRepository.getMemoByTabId(tabId)
    }
}