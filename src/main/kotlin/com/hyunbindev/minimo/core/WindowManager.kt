package com.hyunbindev.minimo.core

import javafx.stage.Stage

object WindowManager {
    private lateinit var primaryStage: Stage
    fun init(primaryStage: Stage) {
        if(::primaryStage.isInitialized) return;
        this.primaryStage = primaryStage
    }

    fun windowClose(){
        primaryStage.close()
    }

    fun toggleAlwaysOnTop():Boolean{
        primaryStage.isAlwaysOnTop = !primaryStage.isAlwaysOnTop
        return primaryStage.isAlwaysOnTop
    }
}