package com.hyunbindev.minimo.component

import javafx.fxml.FXMLLoader
import javafx.scene.layout.VBox

class SideMenuBar : VBox() {
    init {
        val loader = FXMLLoader(javaClass.getResource("/ui/component/SideMenuBar.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        loader.load<Any>()
    }
}