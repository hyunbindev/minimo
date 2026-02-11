package com.hyunbindev.minimo.viewmodel

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.service.TabService
import javafx.beans.property.SimpleIntegerProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.util.Duration
import org.slf4j.LoggerFactory

object TabViewModel {
    private val log = LoggerFactory.getLogger(javaClass)
    val tabList: ObservableList<TabData> = FXCollections.observableArrayList()
    val selectedTabId: SimpleIntegerProperty = SimpleIntegerProperty(-1)

    fun initialize(){
        loadAllTab()
        val initialId = if (tabList.isNotEmpty()) tabList[0].id else -1
        selectedTabId.set(initialId)
    }

    fun createNewTab(){
        val maxIdx = tabList.maxOfOrNull { it.index } ?: 0
        val tabData = TabService.createNewTab(maxIdx+1)
        tabList.add(tabData)
        tabData?.let { selectedTabId.set(it.id) }
    }

    fun loadAllTab(){
        val allTabs:List<TabData> = TabService.getAllTabs()
        tabList.clear()
        tabList.addAll(allTabs)
    }

    fun deleteTab(tabId: Int) {
        TabService.deleteTab(tabId)
        val index = tabList.indexOfFirst { it.id == tabId }
        if (index == -1) return // 없으면 종료

        if (selectedTabId.get() == tabId) {
            MemoViewModel.clearMemoList()

            if (tabList.size > 1) {
                val fallbackTab = if (index > 0) tabList[index - 1] else tabList[index + 1]
                selectedTabId.set(fallbackTab.id)
            }
        }

        tabList.removeAt(index)
    }
}