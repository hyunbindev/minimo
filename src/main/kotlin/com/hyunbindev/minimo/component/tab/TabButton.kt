package com.hyunbindev.minimo.component.tab

import javafx.fxml.FXMLLoader
import javafx.scene.control.Button

class TabButton @JvmOverloads constructor(
    context: String = "New Tab"
): Button() {
    init {
        val loader = FXMLLoader(javaClass.getResource("/ui/component/tab/TabButton.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        try {
            loader.load<Any>()
        }catch (e: Exception){
            throw RuntimeException("Exception occurred while loading tab tab", e)
        }
        this.text = context
    }
}