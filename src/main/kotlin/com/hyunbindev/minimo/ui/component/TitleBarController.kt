package com.hyunbindev.minimo.ui.component

import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.stage.Stage

class TitleBarController {
    private var xOffset: Double = 0.0;
    private var yOffset: Double = 0.0;

    @FXML
    private lateinit var titleBar: HBox

    @FXML
    fun handleMousePressed(event: MouseEvent) {
        xOffset = event.sceneX
        yOffset = event.sceneY
    }

    @FXML
    fun handleMouseDragged(event: MouseEvent) {
        val stage = (event.source as Node).scene.window as Stage
        stage.x = event.screenX - xOffset
        stage.y = event.screenY - yOffset
    }

    @FXML
    fun handleClose(event: MouseEvent) {
        val button = event.source as javafx.scene.Node
        val stage = button.scene.window as Stage
        stage.close()
    }
}