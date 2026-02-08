package com.hyunbindev.minimo.component.memo

import com.hyunbindev.minimo.model.memo.MemoData
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.ScrollPane
import javafx.scene.layout.VBox
import javafx.util.Duration
import org.slf4j.LoggerFactory

class MemoList : ScrollPane(){
    private val log = LoggerFactory.getLogger(MemoList::class.java)
    @FXML
    lateinit var container: VBox
    init {
        val loader = FXMLLoader(javaClass.getResource("/ui/component/memo/MemoList.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        loader.load<Any>()
        setupMemoListListener()

        Platform.runLater {
            MemoViewModel.memoList.forEach { addMemoElementUI(it) }
        }
    }

    private fun setupMemoListListener() {
        MemoViewModel.memoList.addListener(ListChangeListener { change ->
            val addedItems = mutableListOf<MemoData>()
            while (change.next()) {
                if (change.wasAdded()) {
                    addedItems.addAll(change.addedSubList)
                }
                //TODO- 삭제 로직도
            }

            if (addedItems.isNotEmpty()) {
                Platform.runLater {
                    addedItems.forEach { addMemoElementUI(it) }
                }
            }
        })
    }

    private fun addMemoElementUI(memoData: MemoData){
        this.container.children.add(0,MemoElement(memoData))
        scrollToTopWithAnimation()
    }

    /**
     * 스크롤 상위 이동
     */
    private fun scrollToTopWithAnimation() {
        val keyValue = KeyValue(this.vvalueProperty(), 0.0, Interpolator.SPLINE(0.1, 1.0, 0.0, 1.0))

        val keyFrame = KeyFrame(Duration.millis(1000.0), keyValue)

        val timeline = Timeline(keyFrame)
        timeline.play()
    }
}