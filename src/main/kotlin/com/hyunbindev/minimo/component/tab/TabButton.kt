package com.hyunbindev.minimo.component.tab

import com.hyunbindev.minimo.core.TabManager
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.ToggleButton

class TabButton @JvmOverloads constructor(
    context: String = "New Tab"
): ToggleButton() {
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

        this.setOnAction {
            handleTabClick()
        }
    }
    private fun handleTabClick(){
        TabManager.selectTab(this)
    }
}