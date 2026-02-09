package com.hyunbindev.minimo.component.tab

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.viewmodel.TabViewModel
import javafx.collections.ListChangeListener
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class TabFrame @JvmOverloads constructor(): HBox() {
    init{
        val loader = FXMLLoader(javaClass.getResource("/ui/component/tab/TabFrame.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        reRenderTabs()
        loader.load<Any>()
        setupListListener()
    }

    private fun setupListListener() {
        TabViewModel.tabList.addListener(ListChangeListener { change ->
            javafx.application.Platform.runLater {
                while (change.next()) {
                    if (change.wasAdded()) {
                        change.addedSubList.forEach { addTabUI(it) }
                    }
                    if (change.wasRemoved()) {
                        change.removed.forEach { removeTabUI(it) }
                    }
                }
            }
        })
    }

    private fun reRenderTabs() {
        javafx.application.Platform.runLater {
            this.children.clear()

            TabViewModel.tabList.forEach { tabData ->
                val tabButton = TabButton(tabData)
                this.children.add(tabButton)
            }
        }
    }

    private fun addTabUI(tabData: TabData) {
        val tabButton = TabButton(tabData)
        this.children.add(tabButton)
    }

    private fun removeTabUI(tabData: TabData) {
        this.children.removeIf { node ->
            (node as? TabButton)?.tabData?.id == tabData.id
        }
    }
}