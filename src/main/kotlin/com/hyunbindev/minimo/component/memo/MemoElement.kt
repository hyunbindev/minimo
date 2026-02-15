package com.hyunbindev.minimo.component.memo

import com.hyunbindev.minimo.model.memo.MemoData
import com.hyunbindev.minimo.utill.DateUtil
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.scene.text.TextFlow


class MemoElement(val memoData: MemoData) : VBox() {
    @FXML
    private lateinit var content: TextFlow

    @FXML
    private lateinit var updatedAt: Label

    @FXML
    private lateinit var applicationIcon: ImageView

    @FXML
    private lateinit var applicationDescriptionName:Label

    init {
        val loader = FXMLLoader(javaClass.getResource("/ui/component/memo/MemoElement.fxml"))
        loader.setRoot(this)
        loader.setController(this)
        loader.load<Any>()


        val textNode = Text(memoData.content).apply {
            styleClass.add("memo-text")
        }

        this.content.children.add(textNode)
        memoData.updatedAt?.let { this.updatedAt.text = DateUtil.fromLocalDateTimeToFormatString(it) }

        this.content.maxHeight = 100.0

        this.setOnMouseClicked {
            event ->
                val clipboard = Clipboard.getSystemClipboard()
                val content = ClipboardContent()

                // 메모의 전체 내용을 클립보드에 설정
                content.putString(memoData.content)
                clipboard.setContent(content)
        }
    }
}