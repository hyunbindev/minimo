package com.hyunbindev.minimo.viewmodel

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.service.TabService
import javafx.collections.FXCollections
import javafx.collections.ObservableList

object TabViewModel {
    val tabList: ObservableList<TabData> = FXCollections.observableArrayList()
    val selectedTab: ObservableList<TabData> = FXCollections.observableArrayList()
    fun createNewTab(){
        val tabData = TabService.createNewTab(tabList.size)
        tabList.add(tabData)
    }
}