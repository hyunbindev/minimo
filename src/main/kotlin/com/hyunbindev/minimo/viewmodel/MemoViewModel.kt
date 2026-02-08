package com.hyunbindev.minimo.viewmodel

import com.hyunbindev.minimo.model.clipboard.ClipBoardData
import com.hyunbindev.minimo.model.memo.MemoData
import com.hyunbindev.minimo.service.MemoService
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import org.slf4j.LoggerFactory

object MemoViewModel {
    private val log = LoggerFactory.getLogger(MemoViewModel::class.java)

    val memoList: ObservableList<MemoData> = FXCollections.observableArrayList()

    fun initialize(){
        val selectedTabId:Int = TabViewModel.selectedTabId.get()
        memoList.addAll(MemoService.getMemoByTabId(selectedTabId))
    }

    fun createMemoByClip(clip: ClipBoardData){
        val selectedTabId:Int = TabViewModel.selectedTabId.get()
        if(selectedTabId == -1 ) return
        val memo:MemoData = MemoData.fromClipboard(clip, selectedTabId);
        val savedMemo: MemoData = MemoService.createMemo(memo)

        memoList.add(0,savedMemo)
    }

    fun delete(selectedTabId: Int){

    }
}