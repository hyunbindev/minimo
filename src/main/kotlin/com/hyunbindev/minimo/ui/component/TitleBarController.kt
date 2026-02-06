package com.hyunbindev.minimo.ui.component

import com.hyunbindev.minimo.core.WindowManager
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.stage.Stage
import javafx.stage.Window

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
        WindowManager.windowClose()
    }
    @FXML
    fun toggleWindowPin(event: MouseEvent) {
        WindowManager.toggleAlwaysOnTop()
    }
}