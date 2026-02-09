package com.hyunbindev.minimo.core

import javafx.animation.FadeTransition
import javafx.stage.Stage
import javafx.util.Duration
import org.slf4j.LoggerFactory

object WindowManager {
    private val log = LoggerFactory.getLogger(WindowManager::class.java)
    private var fadeTransition: FadeTransition? = null
    private lateinit var PRIMARY_STAGE: Stage
    val PID:Long = ProcessHandle.current().pid()
    fun init(primaryStage: Stage) {
        if(::PRIMARY_STAGE.isInitialized) return;
        this.PRIMARY_STAGE = primaryStage


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
}