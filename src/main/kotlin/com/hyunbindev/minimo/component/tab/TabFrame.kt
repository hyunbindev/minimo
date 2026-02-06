package com.hyunbindev.minimo.component.tab

import com.hyunbindev.minimo.core.TabManager
import javafx.fxml.FXMLLoader
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class TabFrame @JvmOverloads constructor(): HBox() {
    init{
        val loader = FXMLLoader(javaClass.getResource("/ui/component/tab/TabFrame.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        try {
            loader.load<Any>()
        }catch (e: Exception){
            throw RuntimeException("Exception occurred while loading tab frame", e)
        }
        TabManager.init(this)
    }
}