package com.hyunbindev.minimo.component.memo

import com.hyunbindev.minimo.model.memo.MemoData
import com.hyunbindev.minimo.viewmodel.MemoViewModel
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.application.Platform
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
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
            val removedItems = mutableListOf<MemoData>()
            while (change.next()) {
                if (change.wasAdded()) {
                    addedItems.addAll(change.addedSubList)
                }

                if (change.wasRemoved()){
                    removedItems.addAll(change.removed)
                }
            }

            if (addedItems.isNotEmpty()) {
                Platform.runLater {
                    addedItems.forEach { addMemoElementUI(it) }
                }
            }

            if (removedItems.isNotEmpty()) {
                log.info("${removedItems.size} removed item(s)")
                Platform.runLater {
                    // Get all current UI children that are MemoElements
                    val memoElements = this.container.children.filterIsInstance<MemoElement>()

                    // Find the UI elements whose ID matches an ID in the removedItems list
                    val elementsToRemove = memoElements.filter { element ->
                        removedItems.any { removed -> removed.id == element.memoData.id }
                    }

                    // Remove them from the parent container
                    this.container.children.removeAll(elementsToRemove)
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