package com.hyunbindev.minimo.core

import javafx.animation.FadeTransition
import javafx.scene.Cursor
import javafx.stage.Stage
import javafx.util.Duration
import org.jetbrains.exposed.sql.javatime.Year
import org.slf4j.LoggerFactory

object WindowManager {
    private const val RESIZE_MARGIN = 10.0

    private val log = LoggerFactory.getLogger(WindowManager::class.java)
    private var fadeTransition: FadeTransition? = null
    private lateinit var PRIMARY_STAGE: Stage
    val PID:Long = ProcessHandle.current().pid()
    fun init(primaryStage: Stage) {
        if(::PRIMARY_STAGE.isInitialized) return;
        this.PRIMARY_STAGE = primaryStage
        this.enableResizing(primaryStage)

        val root = primaryStage.scene.root

        if (root.minWidth(-1.0) > 0) {
            primaryStage.minWidth = root.minWidth(-1.0)
        }
        if (root.minHeight(-1.0) > 0) {
            primaryStage.minHeight = root.minHeight(-1.0)
        }

        primaryStage.focusedProperty().addListener { _, _, isFocused ->
            if (isFocused) {
                playFadeAnimation(1.0, 200.0)
            } else {
                playFadeAnimation(0.7, 500.0)
            }
        }
    }

    fun windowClose(){
        PRIMARY_STAGE.close()
    }

    fun toggleAlwaysOnTop():Boolean{
        PRIMARY_STAGE.isAlwaysOnTop = !PRIMARY_STAGE.isAlwaysOnTop
        return PRIMARY_STAGE.isAlwaysOnTop
    }
    private fun playFadeAnimation(toValue: Double, ms: Double) {
        fadeTransition?.stop()

        fadeTransition = FadeTransition(Duration.millis(ms), PRIMARY_STAGE.scene.root).apply {
            this.toValue = toValue
            play()
        }
    }

    private fun enableResizing(stage: Stage) {
        val scene = stage.scene ?: return
        var resizeCursor = Cursor.DEFAULT

        scene.setOnMouseMoved { event ->
            val x = event.x
            val y = event.y
            val w = stage.width
            val h = stage.height

            resizeCursor = when {
                // 1. 하단 모서리 (Corner)
                x < RESIZE_MARGIN && y > h - RESIZE_MARGIN -> Cursor.SW_RESIZE
                x > w - RESIZE_MARGIN && y > h - RESIZE_MARGIN -> Cursor.SE_RESIZE

                // 2. 변 (Edge)
                x < RESIZE_MARGIN -> Cursor.W_RESIZE
                x > w - RESIZE_MARGIN -> Cursor.E_RESIZE
                y > h - RESIZE_MARGIN -> Cursor.S_RESIZE

                else -> Cursor.DEFAULT
            }

            scene.cursor = resizeCursor
        }

        scene.setOnMouseDragged { event ->
            if (resizeCursor == Cursor.DEFAULT) return@setOnMouseDragged

            val mouseX = event.screenX
            val mouseY = event.screenY

            // 가로축 계산 (W, SW는 X좌표 이동 포함 / E, SE는 너비만)
            if (resizeCursor in listOf(Cursor.W_RESIZE, Cursor.SW_RESIZE)) {
                val deltaX = stage.x - mouseX
                val newWidth = stage.width + deltaX
                if (newWidth > stage.minWidth) {
                    stage.width = newWidth
                    stage.x = mouseX
                }
            } else if (resizeCursor in listOf(Cursor.E_RESIZE, Cursor.SE_RESIZE)) {
                val newWidth = mouseX - stage.x
                if (newWidth > stage.minWidth) stage.width = newWidth
            }

            // 세로축 계산 (S, SW, SE 모두 높이만 조절 - 상단 고정)
            if (resizeCursor in listOf(Cursor.S_RESIZE, Cursor.SW_RESIZE, Cursor.SE_RESIZE)) {
                val newHeight = mouseY - stage.y
                if (newHeight > stage.minHeight) stage.height = newHeight
            }
        }
    }
}