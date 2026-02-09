package com.hyunbindev.minimo

import com.hyunbindev.minimo.core.ClipboardCaptureManager
import com.hyunbindev.minimo.core.ClipboardMonitor
import com.hyunbindev.minimo.core.WindowManager
import com.hyunbindev.minimo.model.DatabaseFactory
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import com.hyunbindev.minimo.viewmodel.TabViewModel
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.slf4j.LoggerFactory

class MainApplication : Application() {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun init() {
        super.init()
        DatabaseFactory.init()
        TabViewModel.initialize()
        MemoViewModel.initialize()
        ClipboardCaptureManager.initialize()
    }

    override fun start(stage: Stage) {
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

        val scene = Scene(root, 600.0, 800.0, Color.TRANSPARENT);

        scene.stylesheets.addAll(
            javaClass.getResource("/css/global.css")?.toExternalForm(),
            javaClass.getResource("/css/icon.css")?.toExternalForm()
        )

        stage.initStyle(StageStyle.TRANSPARENT)

        stage.title = "MiniMo"
        stage.scene = scene
        stage.show()
        WindowManager.init(stage)
    }
}