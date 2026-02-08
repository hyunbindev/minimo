package com.hyunbindev.minimo.component.tab

import com.hyunbindev.minimo.model.tab.TabData
import com.hyunbindev.minimo.viewmodel.TabViewModel
import javafx.animation.FadeTransition
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.ParallelTransition
import javafx.animation.ScaleTransition
import javafx.animation.Timeline
import javafx.animation.TranslateTransition
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ToggleButton
import javafx.util.Duration
import org.slf4j.LoggerFactory

class TabButton (val tabData: TabData): ToggleButton() {
    private val log = LoggerFactory.getLogger(javaClass)
    @FXML
    lateinit var titleLabel: Label

    @FXML
    lateinit var closeButton: Button

    init {
        val loader = FXMLLoader(javaClass.getResource("/ui/component/tab/TabButton.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        loader.load<Any>()

        if(TabViewModel.selectedTabId.value == tabData.id){
            this.isSelected = true;
        }

        TabViewModel.selectedTabId.addListener { _, _, newId ->
            this.isSelected = tabData.id == newId
        }
        this.titleLabel.text = tabData.title

        playSpawnAnimation()

        closeButton.setOnMouseClicked { event ->
            event.consume()
            playDeleteAnimation{TabViewModel.deleteTab(tabData.id)}
        }
    }

    override fun fire() {
        if (TabViewModel.selectedTabId.value == tabData.id) {
            return
        }
        TabViewModel.selectedTabId.set(tabData.id)
    }

    fun playSpawnAnimation() {
        val fade = FadeTransition(Duration.millis(300.0), this).apply {
            fromValue = 0.0
            toValue = 1.0
        }

        val translate = TranslateTransition(Duration.millis(300.0), this).apply {
            fromX = -30.0
            toX = 0.0
            interpolator = Interpolator.EASE_OUT // 끝날 때 부드럽게 감속
        }

        ParallelTransition(fade, translate).play()
    }


    fun playDeleteAnimation(onFinished: () -> Unit) {
        val fade = FadeTransition(Duration.millis(250.0), this).apply {
            toValue = 0.0
        }


        val translate = TranslateTransition(Duration.millis(250.0), this).apply {
            byX = -30.0
            interpolator = Interpolator.EASE_IN // 시작할 때 부드럽게 가속
        }

        val animation =ParallelTransition(fade ,translate)
        animation.setOnFinished {
            onFinished() // 여기서 비로소 리스트 삭제 및 DB 삭제가 일어납니다.
        }

        // 3. 애니메이션 시작!
        animation.play()
    }

}