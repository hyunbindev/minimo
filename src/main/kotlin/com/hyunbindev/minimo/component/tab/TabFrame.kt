package com.hyunbindev.minimo.component.tab

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
        setupListListener()
        try {
            loader.load<Any>()
        }catch (e: Exception){
            throw RuntimeException("Exception occurred while loading tab frame", e)
        }
    }
    private fun setupListListener() {
        TabViewModel.tabList.addListener(ListChangeListener {
            renderTabs()
        })
        renderTabs()
    }

    private fun renderTabs() {
        javafx.application.Platform.runLater {
            this.children.clear()

            TabViewModel.tabList.forEach { tabData ->
                val tabButton = TabButton(tabData.title).apply {
                    //TODO-변화 감지후 실행 로직
                    setOnAction {  }
                }
                this.children.add(tabButton)
            }
        }
    }
}