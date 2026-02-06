package com.hyunbindev.minimo.controller.component

import com.hyunbindev.minimo.component.tab.TabButton
import com.hyunbindev.minimo.core.WindowManager
import com.hyunbindev.minimo.viewmodel.TabViewModel
import javafx.animation.ScaleTransition
import javafx.application.Platform
import javafx.application.Platform.runLater
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.control.ToggleButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.HBox
import javafx.stage.Stage
import javafx.util.Duration

class TitleBarController {
    private var xOffset: Double = 0.0;
    private var yOffset: Double = 0.0;

    @FXML private lateinit var tabContainer: HBox

    @FXML private lateinit var titleBar: HBox

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
    fun toggleWindowPin(event: MouseEvent?) {
        val isOnTop: Boolean = WindowManager.toggleAlwaysOnTop()

        val toggleBtn: ToggleButton = event?.source as? ToggleButton ?: return

        (toggleBtn).isSelected = isOnTop

        toggleBtn.graphic?.let{
            node ->
            ScaleTransition(Duration.millis(100.0), node).apply{
                fromX = if (isOnTop) 1.0 else 1.3
                fromY = if (isOnTop) 1.0 else 1.3
                toX = if (isOnTop) 1.3 else 1.0
                toY = if (isOnTop) 1.3 else 1.0
            }.play()
        }
    }

    @FXML
    fun createTab() {
        TabViewModel.createNewTab()
    }
}