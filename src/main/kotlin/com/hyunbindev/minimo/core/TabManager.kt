package com.hyunbindev.minimo.core

import com.hyunbindev.minimo.component.tab.TabButton
import eu.hansolo.toolbox.observables.ObservableList
import javafx.collections.FXCollections
import javafx.collections.FXCollections.observableArrayList
import javafx.scene.layout.HBox

object TabManager {
    private lateinit var tabs: List<TabButton>
    private lateinit var container: HBox
    fun init(container: HBox) {
        this.container = container
        this.tabs = container.children.filterIsInstance<TabButton>()
        container.children.add(TabButton("탭 1"))
        container.children.add(TabButton("탭 1"))
        container.children.add(TabButton("탭 1"))
        container.children.add(TabButton("탭 1"))

    }

    fun selectTab(tab: TabButton) {
        container.children.filterIsInstance<TabButton>().forEach {
            it.isSelected = false
        }
        tab.isSelected = true
    }
}