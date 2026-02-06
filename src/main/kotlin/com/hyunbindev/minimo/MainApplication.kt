package com.hyunbindev.minimo

import com.hyunbindev.minimo.core.WindowManager
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
        //window manager init
        WindowManager.init(stage)
        //get font resource
        val fontStream = javaClass.getResource("/fonts/defaultFont.ttf")?.toExternalForm()

        if(fontStream != null) {
            Font.loadFont(fontStream,12.0)
            val loadedFont: Font? = Font.loadFont(fontStream, 14.0)
        }else{
            throw RuntimeException("Could not load font")
        }

        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("/ui/view/main/main-shell.fxml"))
        val root = fxmlLoader.load<Parent>()

        val scene = Scene(root, 320.0, 240.0, Color.TRANSPARENT);

        //css load
        //val cssPath = javaClass.getResource("/css/global.css")?.toExternalForm()
        //if(cssPath != null) {scene.stylesheets.add(cssPath) }

        scene.stylesheets.addAll(
            javaClass.getResource("/css/global.css")?.toExternalForm(),
            javaClass.getResource("/css/icon.css")?.toExternalForm()
        )

        stage.initStyle(StageStyle.TRANSPARENT)

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}