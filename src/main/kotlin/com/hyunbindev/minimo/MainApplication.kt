package com.hyunbindev.minimo

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.stage.StageStyle

class MainApplication : Application() {
    override fun start(stage: Stage) {

        //get font resource
        val fontStream = javaClass.getResource("/fonts/defaultFont.ttf")?.toExternalForm()

        if(fontStream != null) {
            Font.loadFont(fontStream,12.0)
            val loadedFont: Font? = Font.loadFont(fontStream, 14.0)
            println("디버그 - 로드된 폰트 패밀리 이름: ${loadedFont?.family}")
        }else{
            throw RuntimeException("Could not load font")
        }

        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("/ui/view/main/main-shell.fxml"))
        val root = fxmlLoader.load<Parent>()

        val scene = Scene(root, 320.0, 240.0, Color.TRANSPARENT);

        val cssPath = javaClass.getResource("/css/style.css")?.toExternalForm()
        if(cssPath != null) {scene.stylesheets.add(cssPath) }

        stage.initStyle(StageStyle.TRANSPARENT)

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}